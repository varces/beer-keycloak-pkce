import axios from 'axios'
import { config } from '../../Constants'

export const beersApi = {
  getBeers,
  getBeer,
  saveBeer,
  deleteBeer,
  addBeerComment,
  getUserExtrasMe,
  saveUserExtrasMe
}

function getBeers() {
  return instance.get('/api/beers')
}

function getBeer(id) {
  return instance.get(`/api/beers/${id}`)
}

function saveBeer(beer, token) {
  return instance.post('/api/beers', beer, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(token)
    }
  })
}

function deleteBeer(id, token) {
  return instance.delete(`/api/beers/${id}`, {
    headers: { 'Authorization': bearerAuth(token) }
  })
}

function addBeerComment(id, comment, token) {
  return instance.post(`/api/beers/${id}/comments`, comment, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(token)
    }
  })
}

function getUserExtrasMe(token) {
  return instance.get(`/api/userextras/me`, {
    headers: { 'Authorization': bearerAuth(token) }
  })
}

function saveUserExtrasMe(token, userExtra) {
  return instance.post(`/api/userextras/me`, userExtra, {
    headers: { 'Authorization': bearerAuth(token) }
  })
}

// -- Axios

const instance = axios.create({
  baseURL: config.url.API_BASE_URL
})

instance.interceptors.response.use(response => {
  return response;
}, function (error) {
  if (error.response.status === 404) {
    return { status: error.response.status };
  }
  return Promise.reject(error.response);
});

// -- Helper functions

function bearerAuth(token) {
  return `Bearer ${token}`
}