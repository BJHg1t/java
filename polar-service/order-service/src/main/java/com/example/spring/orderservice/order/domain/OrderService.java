package com.example.spring.orderservice.order.domain;

import com.example.spring.orderservice.book.Book;
import com.example.spring.orderservice.book.BookClient;
import com.example.spring.orderservice.event.OrderAcceptedMessage;
import com.example.spring.orderservice.event.OrderDispatchedMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.spring.orderservice.order.domain.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookClient bookClient; // @Component 사용했음
    private final static Logger log = LoggerFactory.getLogger(OrderService.class);
    private final StreamBridge streamBridge; // rabbitmq로 던지기 위한 것

    // 주문 내역 전체 조회, flux는 복수 건에 사용하기 때문에 List로 묶을 필요 없음
    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

    public Mono<Order> submitOrder(String isbn, int quantity) {
        return bookClient.getBookByIsbn(isbn)
                .map(book -> buildAcceptedOrder(book, quantity)) // map을 통해 book 객체 > order 객체
                .defaultIfEmpty(buildRejectedOrder(isbn, quantity))
                .flatMap(orderRepository::save) // flatMap : Mono<Mono<Order>>을 평탄화 > Mono<Order>, 람다 문법 (order -> orderRepository.save(order))
                .doOnNext(this::publishOrderAcceptedEvent);
    }

    public Flux<Order> consumerOrderDispatchedEvent(Flux<OrderDispatchedMessage> orderDispatchedMessageFlux) {
        return orderDispatchedMessageFlux
                .flatMap(orderDispatchedMessage -> orderRepository.findById(orderDispatchedMessage.orderId()))
                .map(this::buildDispatchedOrder)
                .flatMap(orderRepository::save);
    }

    private static Order buildAcceptedOrder(Book book, int quantity) {
        return Order.builder()
                .bookIsbn(book.isbn())
                .bookName(book.title() + "-" + book.author())
                .bookPrice(book.price())
                .quantity(quantity)
                .status(ACCEPTED) // OrderStatus.ACCEPTED > alt + enter > 2번째 Add
                .build();
    }

    private static Order buildRejectedOrder(String bookIsbn, int quantity) {
        return Order.builder()
                .bookIsbn(bookIsbn)
                .quantity(quantity)
                .status(REJECTED)
                .build();
    }

    private void publishOrderAcceptedEvent(Order order) {
        if (!order.status().equals(ACCEPTED)) {
            return;
        }

        OrderAcceptedMessage orderAcceptedMessage = new OrderAcceptedMessage(order.id());
        log.info("Publishing order aceepted event id : {}", orderAcceptedMessage.orderId());

        boolean result = streamBridge.send("acceptOrder-out-0", orderAcceptedMessage);
        log.info("Result of sending order aceepted event : {}, id : {}", result, orderAcceptedMessage.orderId());

    }

    private Order buildDispatchedOrder(Order existingOrder) {
        return Order.builder()
                .id(existingOrder.id())
                .bookIsbn(existingOrder.bookIsbn())
                .bookName(existingOrder.bookName())
                .bookPrice(existingOrder.bookPrice())
                .quantity(existingOrder.quantity())
                .status(DISPATCHED)
                .createdDate(existingOrder.createdDate())
                .lastModifiedDate(existingOrder.lastModifiedDate())
                .version(existingOrder.version())
                .build();
    }

}