
## work-queues-test module description
### 1.target
- Test that multiple consumers consume the same queue based on processing speed.
- Test whether uncommitted records can be reassigned to other consumers when an exception occurs in a consumer.
- Test RabbitMQ persistence. RabbitMQ is down and can be consumed normally after restarting.
### 2.RabbitMQ mode
Work queues

Fair dispatch:

https://www.enterpriseintegrationpatterns.com/patterns/messaging/CompetingConsumers.html

### 3.RabbitMQ Management

URL: http://10.113.97.64:15672/#/queues

User: cmsuser

Password: cmspwd

### 4.How to test
- run ConsumerMsg1
- run ConsumerMsg2
- run SendMsg
- review ConsumerMsg1 result




