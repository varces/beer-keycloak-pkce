const prod = {
  url: {
    KEYCLOAK_BASE_URL: "http://keycloak:8080",
    API_BASE_URL: 'http://localhost:9080',
  }
}

const dev = {
  url: {
    KEYCLOAK_BASE_URL: "http://localhost:8080",
    API_BASE_URL: 'http://localhost:9080',
  }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod