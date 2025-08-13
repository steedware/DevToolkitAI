# AI-Based Dev Toolkit

A Spring Boot web application that integrates with OpenAI's API to assist developers with daily tasks: code review, test generation, commit messages, bug analysis, and interview preparation.

Built to practice advanced Spring Boot concepts, AI integration, and full-stack development skills as a Junior Java Developer.

## Demo
link: https://devtoolkitai.onrender.com/

## What it does

The application provides 5 AI-powered tools accessible via REST API and web interface:

| Feature | What it does | API Endpoint |
|---------|-------------|--------------|
| **Code Review** | Analyzes Java code and provides quality feedback | `POST /api/review` |
| **Test Generator** | Generates JUnit5 test cases for your classes | `POST /api/tests/generate` |
| **Commit Messages** | Creates conventional commit messages from git diffs | `POST /api/commit/message` |
| **Bug Explainer** | Explains stack traces and suggests solutions | `POST /api/bug/explain` |
| **Interview Helper** | Evaluates technical interview answers with feedback | `POST /api/interview/answer-check` |

## Architecture

Clean layered Spring Boot architecture:

```
src/main/java/com/steedware/toolkit/
├── AiDevToolkitApplication.java     # Main Spring Boot class
├── controller/                      # REST API endpoints (5 controllers)
├── service/                         # Business logic layer (6 services)
├── dto/                            # Request/Response objects (10 DTOs)
└── config/                         # Configuration and exception handling
```

**Key design patterns implemented:**
- **Dependency Injection** - Spring IoC container
- **Service Layer Pattern** - Separation of business logic
- **DTO Pattern** - Clean data transfer with validation
- **Template Method** - Common AI response parsing logic

## Technology Stack

- **Java 21** - Modern Java features
- **Spring Boot 3** - Framework with auto-configuration
- **Spring Web** - REST API development
- **Spring Validation** - Bean validation with annotations
- **OpenAI API** - AI integration via RestTemplate
- **Bootstrap 5** - Responsive frontend framework
- **JUnit 5 + Mockito** - Unit and integration testing
- **Swagger/OpenAPI** - API documentation
- **Maven** - Dependency management
- **Docker** - Containerization

## Frontend

Responsive web interface built with Bootstrap 5:
- **5 interactive cards** - One for each AI feature
- **Real-time API calls** - Async JavaScript with fetch API
- **Loading indicators** - User feedback during AI processing
- **Error handling** - Graceful error display
- **Mobile responsive** - Works on all screen sizes

## Quick Start

### Prerequisites:
- Java 21+
- Maven 3.6+
- OpenAI API key

### Setup:
1. **Clone repository:**
```bash
git clone https://github.com/steedware/ai-dev-toolkit.git
cd ai-dev-toolkit
```

2. **Configure API key:**
```bash
# Windows
set OPENAI_API_KEY=your-api-key-here

# Linux/Mac  
export OPENAI_API_KEY=your-api-key-here
```

3. **Build and run:**
```bash
mvn clean install
mvn spring-boot:run
```

4. **Access application:**
- **Web UI**: `http://localhost:8080`
- **API Docs**: `http://localhost:8080/swagger-ui.html`

## API Examples

### Code Review:
```bash
curl -X POST http://localhost:8080/api/review \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Calculator { public int add(int a, int b) { return a + b; } }",
    "fileName": "Calculator.java",
    "language": "java"
  }'
```

### Test Generation:
```bash
curl -X POST http://localhost:8080/api/tests/generate \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class StringUtils { public boolean isEmpty(String str) { return str == null || str.trim().isEmpty(); } }",
    "className": "StringUtils",
    "testFramework": "junit5"
  }'
```

## Configuration

Application settings in `application.yml`:
```yaml
ai:
  openai:
    api-key: ${OPENAI_API_KEY}
    api-url: https://api.openai.com/v1/chat/completions
    model: gpt-3.5-turbo
    max-tokens: 2000
    temperature: 0.3

server:
  port: 8080

spring:
  application:
    name: ai-dev-toolkit
```

## Testing

Comprehensive test suite with good coverage:
```bash
mvn test                    # Run all tests
mvn test -Dtest="*ControllerTest"     # Integration tests
mvn test -Dtest="*ServiceTest"        # Unit tests
```

**Testing approaches used:**
- **Unit tests** - Service layer with Mockito
- **Integration tests** - Controller endpoints with MockMvc
- **Validation tests** - DTO constraint testing

## Key Implementation Details

### AI Service Integration
- **RestTemplate** for HTTP client
- **JSON response parsing** with Jackson ObjectMapper
- **Error handling** for API failures
- **Configurable parameters** (model, temperature, tokens)

### Frontend JavaScript
- **Async/await** for API calls
- **Error handling** with try-catch blocks
- **DOM manipulation** for dynamic content
- **Form validation** before submission

### Spring Boot Features
- **Bean Validation** with `@Valid` annotations
- **Global Exception Handler** for centralized error management
- **Configuration Properties** for externalized config
- **Actuator** for health checks and monitoring

## What I learned building this

- **Advanced Spring Boot** - Configuration, validation, exception handling
- **REST API design** - Proper HTTP methods, status codes, error responses
- **External API integration** - HTTP clients, JSON parsing, error handling
- **Testing strategies** - Unit vs integration tests, mocking external services
- **Frontend-backend integration** - CORS, JSON serialization, async calls
- **AI prompt engineering** - Crafting effective prompts for different use cases
- **Documentation** - OpenAPI/Swagger for API documentation

## Challenges overcome

- **AI response consistency** - Handling unpredictable AI output formats
- **Error handling complexity** - Managing multiple failure points (API, parsing, validation)
- **Frontend state management** - Loading states, error display, form reset
- **Testing external APIs** - Mocking RestTemplate calls effectively
- **Configuration management** - Secure API key handling

## Future enhancements

Planned improvements as I continue learning:
- [ ] **Caching layer** - Redis for AI response caching
- [ ] **Rate limiting** - Prevent API abuse
- [ ] **Multi-language support** - Python, TypeScript analysis
- [ ] **Batch processing** - Multiple file analysis
- [ ] **Authentication** - User management with Spring Security
- [ ] **Metrics collection** - Application monitoring

## Docker Deployment

```bash
# Build image
docker build -t ai-dev-toolkit .

# Run container
docker run -d \
  -p 8080:8080 \
  -e OPENAI_API_KEY=your-api-key \
  --name ai-toolkit \
  ai-dev-toolkit
```

## Troubleshooting

**Port 8080 already in use:**
```bash
# Find and kill process
netstat -ano | findstr :8080
taskkill /PID <process-id> /F
```

**OpenAI API issues:**
- Verify API key is set: `echo $OPENAI_API_KEY`
- Check API key validity in OpenAI dashboard
- Ensure sufficient API credits

**Build failures:**
```bash
mvn clean install -U    # Force update dependencies
mvn clean install -DskipTests    # Skip tests if needed
```

## Contributing

Contributions welcome! This project follows standard practices:
1. Fork the repository
2. Create feature branch
3. Write tests for new functionality
4. Ensure all tests pass
5. Submit pull request with clear description

## Author

**steedware** - [GitHub](https://github.com/steedware)

## License

MIT License - feel free to use and modify!
