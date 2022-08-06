# springboot-rabbitmq
Repository to understand advance concepts of RabbitMQ.

### Install RabbitMQ
The following steps explain how to install and run RabbitMQ locally:

- Install [RabbitMQ](https://www.rabbitmq.com/download.html) and [Erlang](https://www.erlang.org/downloads).
- Go to `C:\Program Files\RabbitMQ Server\rabbitmq_server-3.10.7\sbin` and execute `rabbitmq-server.bat start`. Wait for the broker to start.
- Go to `C:\Program Files\RabbitMQ Server\rabbitmq_server-3.10.7\sbin` and execute `rabbitmq-plugins enable rabbitmq_management`.
- Browse to [http://localhost:15672/](http://localhost:15672/) and login as `guest`:`guest`.

### Producer

- Connection is establised not at the boot time but when RabbitTemplate calls `convertAndSend`.
- If the exchange and queues are not created manually, RabbitTemplate will create them when `convertAndSend` method is called.
- `MessageConfig` class should have Exchange, Queue and QueueBinding declarations in Producer. This need not be implemented in Consumers.
- Refer dead letter configurations set for `active deal` queue and `closed deal` queue in `MessageConfig`.
- If you manually create queues, make sure that all arguments match what is there in the `MessageConfig` class otherwise the Producer will throw exceptions and publish will fail. If the queue is created via producer and new arguements are added to `MessageConfig` later, it will still fail. Match the arguements via RabbitMQ UI or recreate queues to fix runtime broker issues.
- Use thunderclient request to POST messages. Deals with ID less than 10 will trigger dead letter retry.

### Consumer

- Connection is establised at boot time.
- Retry configurations are included inside `application.properties` file.
- Consumer need not have `MessageConfig` with Exchange, Queue and QueueBindings declarations but you can store queue names in it for `RabbitListener`.
- After retries are exhausted `InvalidDealException` basically sends a NACK back to the queue. This helps queue redirect the message to dead letter exchange.
