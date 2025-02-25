# reseller-order
In this project, we simulate the beverage order (supplier) flow that resellers handle on a daily basis.


  Reseller           Order Recieving API    Supplier API
    |                     |                     |
    |  createOrder()      |                     |
    |-------------------> |                     |
    |                     | validateOrder()     |
    |                     |-------------------> |
    |                     |                     |  validate()
    |                     |                     |<-----------------|
    |                     | sendOrder()         |
    |                     |-------------------> |
    |                     |                     |  sendOrder()
    |                     |                     |<-----------------|
    |                     | receiveConfirmation()|
    |                     |-------------------> |
    |                     |                     |  confirmation
    |                     |<------------------- |
    |     confirm()       |                     |
    |<------------------- |                     |
    |                     |                     |
