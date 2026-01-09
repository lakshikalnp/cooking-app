# 🍳 Cooking App – AI Powered Recipe Generator

A Spring Boot application that generates cooking recipes using AI, stores recipes with ingredients and instructions, and maintains a searchable log of all generated prompts.

---

## 🚀 Features

- AI-powered recipe generation
- Recipes based on prompt + number of people
- Persistent storage of recipes and ingredients
- One-to-one recipe logging
- Pagination support for logs
- UUID-based primary keys
- Clean JPA entity mappings
- Conditional customization with `isCustomized` and `noOfGramsOnePersonEats`

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate 6
- MySQL
- Lombok
- MapStruct
- OpenAI API
- Maven


---

## 📡 REST APIs

### Base URL
```
/V1/recipes
```

### 1. Generate Recipe

**POST /**

**Description:** Generates a recipe based on the number of people, a prompt, and optional customization.

**Request Body:**
```json
{
  "people": 4,
  "prompt": "Chicken curry with rice",
  "isCustomized": true,
  "noOfGramsOnePersonEats": 100
}
```

**Response:**
```json
{
  "id": "uuid",
  "recipeName": "Chicken Curry",
  "ingredients": [...],
  "instructions": "...",
  "noOfGramsOnePersonEats": 100
}
```

---

### 2. Get All Recipes

**GET /**

**Description:** Retrieves all recipe logs with pagination.

**Query Parameters:**
- `page` (default: 0) - Page number
- `size` (default: 10) - Number of items per page
- `sort` (default: createdAt,desc) - Sorting order

**Response:**
```json
{
  "content": [
    {
      "id": "uuid",
      "recipeName": "Chicken Curry",
      "prompt": "Chicken curry with rice",
      "requestedPeople": 4,
      "noOfGramsOnePersonEats": 100,
      "createdAt": "2025-12-31T10:00:00",
      "recipeId": "uuid"
    }
  ],
  "pageable": {...},
  "totalPages": 5,
  "totalElements": 50
}
```

---

### 3. Get Recipe by ID

**GET /{id}**

**Description:** Retrieves a recipe by its unique ID.

**Path Parameter:**
- `id` - UUID of the recipe

**Response:**
```json
{
  "id": "uuid",
  "recipeName": "Chicken Curry",
  "noOfGramsOnePersonEats": 100,
  "ingredients": [...],
  "instructions": "..."
  
}
```

---

## 🔐 Configuration

⚠️ **Never commit secrets to GitHub**

`application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cooking_app
spring.datasource.username=root
spring.datasource.password=yourpassword

openai.api.key=${OPENAI_API_KEY}
```

Set environment variable:
```bash
export OPENAI_API_KEY=your_api_key
```

---

## ▶️ Run the Project

```bash
mvn clean install
mvn spring-boot:run
```

Application runs at: `http://localhost:8080`

---

## ✅ Best Practices Used

- DTO-based API responses
- Pagination for large datasets
- Lazy loading for JPA relationships
- UUID identifiers
- Proper cascade handling
- Conditional recipe customization

---

## 🚧 Future Enhancements

- User authentication
- Recipe search and filters
- Favorites
- Swagger / OpenAPI documentation
- Caching AI responses

---

## 👩‍💻 Author

**Lakshika Perera**