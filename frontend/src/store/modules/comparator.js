export const namespaced = true

export const state = {
  attacker: null,
  defender: null
}

export const mutations = {
  REMOVE_CARDS(state) {
    state.attacker = null
    state.defender = null
  },
  SET_ATTACKER(state, card) {
    state.attacker = card
  },
  SET_DEFENDER(state, card) {
    state.defender = card
  }
}

export const actions = {
  removeCards({ commit }) {
    commit('REMOVE_CARDS')
  },
  setAttacker({ commit }, card) {
    commit('SET_ATTACKER', card)
  },
  setDefender({ commit }, card) {
    commit('SET_DEFENDER', card)
  }
}
