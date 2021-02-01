export const namespaced = true

export const state = {
  deck: Array,
  points: 0,
  cards: 0,
  score: 0
}

export const mutations = {
  ADD_CARDS(state, cards) {
    state.cards = cards
  },
  ADD_POINTS(state, points) {
    state.points = points
  },
  ADD_SCORE(state, score) {
    state.score = score
  },
  REMOVE_CARD(state, card) {
    state.deck[card.suitID].suitCards.splice(card.cardID, 1)
    state.cards--
  },
  REMOVE_DECK(state) {
    state.deck = []
  },
  SET_DECK(state, handDeck) {
    for (let suit in handDeck) {
      state.deck.push({
        suit: suit,
        suitCards: handDeck[suit]
      })
    }
  }
}

export const actions = {
  addCards({ commit }, cardsNumber) {
    commit('ADD_CARDS', cardsNumber)
  },
  addPoints({ commit }, points) {
    commit('ADD_POINTS', points)
  },
  addScore({ commit }, score) {
    commit('ADD_SCORE', score)
  },
  removeCard({ commit }, card) {
    commit('REMOVE_CARD', card)
  },
  removeDeck({ commit }) {
    commit('REMOVE_DECK')
  },
  setDeck({ commit }, handDeck) {
    commit('REMOVE_DECK')
    commit('SET_DECK', handDeck)
  }
}
