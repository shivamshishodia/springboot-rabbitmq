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

### Consumer

- Connection is establised at boot time.
