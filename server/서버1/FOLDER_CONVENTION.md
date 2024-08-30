아래 로직에서 Repository, Service, Dto, Entity는 따로 폴더 빼서 만들어주셔도 좋습니다.

```
project/
├── src/
│   ├── config/
|   |   ├── security/
│   │   │   ├── jwtConfiguration.java
│   │   │   └── securityConfiguration.java
│   │   ├── WebConfiguration.java       # Web configuration
│   │   └── SwaggerConfiguration.java   # Swagger configuration
│   ├── domain/
│   │   ├── user/
│   │   │   ├── User.java                # Entity
│   │   │   ├── UserDto.java             # DTO
│   │   │   ├── UserRepository.java      # Repository Interface
│   │   │   ├── UserService.java         # Service Interface
│   │   │   └── UserServiceImpl.java     # Service Implementation
│   │   ├── product/
│   │   │   ├── Product.java             # Entity
│   │   │   ├── ProductDto.java          # DTO
│   │   │   ├── ProductRepository.java   # Repository Interface
│   │   │   ├── ProductService.java      # Service Interface
│   │   │   └── ProductServiceImpl.java  # Service Implementation
│   │   └── order/
│   │       ├── Order.java               # Entity
│   │       ├── OrderDto.java            # DTO
│   │       ├── OrderRepository.java     # Repository Interface
│   │       ├── OrderService.java        # Service Interface
│   │       └── OrderServiceImpl.java    # Service Implementation
│   └── interfaces/
│       ├── controllers/
│       │   ├── UserController.java   # Controller for User
│       │   ├── ProductController.java  # Controller for Product
│       │   └── OrderController.java  # Controller for Order
│       └── api/
│       │   ├── UserApi.java   # API for User
│       │   ├── ProductApi.java  # API for Product
│       │   └── OrderApi.java  # API for Order
|       └── advice/
│           └── AdviceController.java  # Controller Advice
├── tests/
├── config/
│   ├── WebConfiguration.java       # Web configuration
│   └── SwaggerConfiguration.java   # Swagger configuration
└── docs/
```
