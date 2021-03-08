import axios from 'axios'

const apiClient = axios.create({
  withCredentials: false,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  }
})

export default {
  compareCards(comparator) {
    return apiClient.post('/compare', comparator)
  },
  getParty() {
    console.log(apiClient.get('/party'))
    return apiClient.get('/party')
  },
  getTurn() {
    console.log(apiClient.get('/turn'))
    return apiClient.get('/turn')
  },
  playTrump(owner, trump) {
    return apiClient.post(
      '/playTrump',
      JSON.parse('{ "suit": "' + trump + '", "owner": "' + owner + '" }')
    )
  },
  reset() {
    return apiClient.post('/reset')
  },
  switchTrump() {
    return apiClient.post('/switchTrump')
  },
  switchPhase() {
    return apiClient.post('/switchPhase')
  }
}
