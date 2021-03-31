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
    return apiClient.get('/party')
  },
  getTurn() {
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
  save() {
    return apiClient.post('/save')
  },
  load() {
    return apiClient.post('/load')
  },
  switchTrump() {
    return apiClient.post('/switchTrump')
  },
  switchPhase() {
    return apiClient.post('/switchPhase')
  }
}
