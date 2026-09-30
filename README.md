# 🍳 RecipeMate

A full-stack recipe assistant that helps home cooks discover ingredients, quantities, and nutritional information for any dish — with AI-powered fallback, cooking mode, and personal recipe memories.

**Live Demo:** [Frontend](#) · [Backend API](#)
*(replace with your deployed Vercel and Render URLs)*

---

## 📖 Overview

RecipeMate solves a simple problem: beginner cooks often don't know what ingredients a dish needs, in what quantity, or its nutritional value. Search any dish name and get a complete breakdown — ingredients, quantities, nutrition, and step-by-step instructions — sourced from a live recipe API with an AI fallback for dishes it can't find.

Built as a portfolio project to demonstrate full-stack development: REST API design, external API integration, caching strategies, authentication, cloud storage, and AI integration.

---

## ✨ Features

- 🔍 **Dish Search** — enter any dish name and get ingredients, quantities, and nutrition
- 🔄 **Cache-Aside Pattern** — checks the database first, falls back to the Spoonacular API, then AI (Groq) if the dish isn't found anywhere — with results cached for future searches
- 📏 **Serving-Size Scaler** — dynamically recalculates ingredient quantities for any number of servings
- ❤️ **Saved Recipes** — logged-in users can save recipes to their personal collection
- 📝 **Personal Notes** — add private notes/tweaks to any recipe
- 🔁 **Reverse Ingredient Search** — enter ingredients you already have, get dish suggestions ranked by best match
- 🔐 **JWT Authentication** — secure registration/login with stateless token-based auth
- 📸 **Photo Memory Journal** — upload a photo of your cooked dish (stored via Cloudinary) with a caption, building a personal cooking journal
- 🔄 **Ingredient Substitutions** — static database lookup with AI fallback for ingredients not covered
- 👨‍🍳 **Cooking Mode** — step-by-step instructions with auto-advancing timers and screen wake lock (keeps the screen on while cooking)

---

## 🛠️ Tech Stack

### Backend
- **Java 17**, **Spring Boot 3.5**
- **Spring Data JPA** + **MySQL** (hosted on Aiven)
- **Spring Security** with **JWT** (jjwt 0.12.6)
- **Spoonacular API** — primary recipe/nutrition data source
- **Groq API** (Llama 3.1) — AI fallback for recipe generation and ingredient substitutions
- **Cloudinary** — cloud image storage for the photo memory journal

### Frontend
- **React** (Vite)
- **Tailwind CSS v4** — styling
- **React Router DOM** — client-side routing
- **Axios** — API communication with JWT interceptor

### Deployment
- **Backend:** Render (Docker deployment)
- **Database:** Aiven (managed MySQL)
- **Frontend:** Vercel

---

## 🏗️ Architecture Highlights

**Cache-Aside Pattern with a Three-Tier Fallback**
When a user searches for a dish, the system:
1. Checks the local database (by search term, then by canonical recipe ID)
2. Falls back to the Spoonacular API if not cached
3. Falls back further to an AI model (Groq) if Spoonacular has no match
4. Persists the result so future searches for the same dish are served instantly from the database

**Normalized Data Design**
Saved recipes are stored as a join entity (`user_id`, `recipe_id`, `saved_at`) rather than duplicating recipe data — full recipe details are expanded into the response DTO, keeping the database as a single source of truth.

**Stateless Authentication**
JWT-based auth with Spring Security, using a custom `OncePerRequestFilter` to validate tokens on protected routes, with public endpoints (search, substitutions) explicitly permitted.

---

## 🚀 Getting Started (Local Setup)

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL (local or remote)
- API keys: [Spoonacular](https://spoonacular.com/food-api), [Groq](https://console.groq.com), [Cloudinary](https://cloudinary.com)

### Backend

```bash
git clone https://github.com/YOUR_USERNAME/recipemate-backend.git
cd recipemate-backend
```

Create environment variables (or a local `application.yml`) with:

```yaml
DB_URL=jdbc:mysql://localhost:3306/recipemate_db
DB_USERNAME=root
DB_PASSWORD=your_password
SPOONACULAR_API_KEY=your_key
GROQ_API_KEY=your_key
CLOUDINARY_CLOUD_NAME=your_name
CLOUDINARY_API_KEY=your_key
CLOUDINARY_API_SECRET=your_secret
JWT_SECRET=your_secret_min_32_chars
```

Run:
```bash
./mvnw spring-boot:run
```
Backend runs on `http://localhost:8084`.

### Frontend

```bash
git clone https://github.com/YOUR_USERNAME/recipemate-frontend.git
cd recipemate-frontend
npm install
```

Update `src/api/axiosInstance.js` with your backend URL, then:
```bash
npm run dev
```
Frontend runs on `http://localhost:5173`.

---

## 📡 API Overview

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|----------------|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Login, returns JWT | No |
| GET | `/api/recipes/search?name=` | Search a dish | No |
| GET | `/api/recipes/{id}/scale?servings=` | Get scaled ingredients | No |
| POST | `/api/recipes/search-by-ingredients` | Reverse ingredient search | No |
| GET | `/api/substitutions?ingredient=` | Get an ingredient substitute | No |
| POST | `/api/users/{userId}/saved-recipes/{recipeId}` | Save a recipe | Yes |
| GET | `/api/users/{userId}/saved-recipes` | List saved recipes | Yes |
| POST | `/api/users/{userId}/recipes/{recipeId}/notes` | Add a note | Yes |
| POST | `/api/users/{userId}/recipes/{recipeId}/photos` | Upload a memory photo | Yes |
| GET | `/api/users/{userId}/photos` | List all memory photos | Yes |

---

## 🧠 What I Learned

- Designing a multi-tier fallback system (DB → external API → AI) that balances cost, latency, and data availability
- Handling bidirectional JPA relationships and Jackson serialization pitfalls (circular references, `@JsonManagedReference`/`@JsonBackReference`)
- Implementing stateless JWT authentication with Spring Security from scratch
- Integrating a third-party LLM (Groq) with structured JSON-only prompting for reliable, parseable output
- Abstracting file storage behind a service layer to allow swapping local storage for Cloudinary with minimal code change
- Deploying a full-stack app across three separate free-tier platforms (Render, Aiven, Vercel) and debugging real production issues (JDBC URL formatting, CORS, environment variable handling)

---

## 📌 Future Improvements

- Structured step data for cooking mode (currently parsed client-side from a single instructions string)
- Full test coverage (unit + integration tests)
- Recipe search pagination and filtering
- Social features (sharing saved recipes with friends)

---

## 📄 License

This project is for educational/portfolio purposes.
