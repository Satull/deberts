export const namespaced = true

export const state = {
  suit: '',
  card: null
}

export const mutations = {
  REMOVE_TRUMP(state) {
    state.trump = []
  },
  SET_TRUMP(state, handDeck) {
    for (let suit in handDeck) {
      state.suit = suit
      state.card = handDeck[suit][0]
    }
  }
}

export const actions = {
  setTrump({ commit }, handDeck) {
    commit('REMOVE_TRUMP')
    commit('SET_TRUMP', handDeck)
  }
}
