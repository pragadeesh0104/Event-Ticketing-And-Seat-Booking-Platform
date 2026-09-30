# Eventify — Production Deployment Guide

## Prerequisites
- Deployed MySQL 8 instance (Railway, Aiven, or AWS RDS).
- Backend hosting platform (Render, Railway, or AWS Elastic Beanstalk).
- Frontend hosting platform (Vercel or Netlify).

## Environment Variables Configuration

### Backend (.env / Production Env Vars)
```env
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=prod
DB_HOST=your-db-host.railway.app
DB_PORT=3306
DB_NAME=eventify_db
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
JWT_EXPIRATION_MS=86400000
CORS_ALLOWED_ORIGINS=https://eventify-frontend.vercel.app
```

### Frontend (.env.production)
```env
VITE_API_BASE_URL=https://eventify-backend.onrender.com/api/v1
```

## Build Commands
- **Backend Jar**: `mvn clean package -DskipTests=false`
- **Frontend SPA**: `npm run build`
