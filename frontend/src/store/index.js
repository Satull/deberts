import { createStore } from 'vuex'
import * as player from '@/store/modules/player'
import * as bot from '@/store/modules/bot'
import * as trump from '@/store/modules/trump'
import * as comparator from '@/store/modules/comparator'

export default createStore({
  state: {
    deckCards: 0,
    newGame: true,
    passes: 0,
    phase: 0,
    roundHistory: {},
    roundTurn: String,
    switchAllowed: false,
  },
  modules: {
    player,
    bot,
    trump,
    comparator,
  },
  mutations: {
    ADD_PASSES(state) {
      state.passes++
    },
    SET_DECK_CARDS(state, cardsNumber) {
      state.deckCards = cardsNumber
    },
    SET_PASSES(state, passes) {
      state.passes = passes
    },
    SET_PHASE(state, phase) {
      state.phase = phase
    },
    SET_ROUND_TURN(state, roundTurn) {
      state.roundTurn = roundTurn
    },
    SET_ROUND_HISTORY(state, roundHistory) {
      state.roundHistory = roundHistory
    },
    SET_SWITCH_ALLOWED(state, switchAllowed) {
      state.switchAllowed = switchAllowed
    },
  },
  actions: {
    addPasses({ commit }) {
      console.log('HELLO AGAIN')
      commit('ADD_PASSES')
    },
    setDeckCards({ commit }, cardsNumber) {
      commit('SET_DECK_CARDS', cardsNumber)
    },
    setPasses({ commit }, passes) {
      commit('SET_PASSES', passes)
    },
    setPhase({ commit }, phase) {
      commit('SET_PHASE', phase)
    },
    setRoundTurn({ commit }, roundTurn) {
      commit('SET_ROUND_TURN', roundTurn)
    },
    setRoundHistory({ commit }, roundHistory) {
      commit('SET_ROUND_HISTORY', roundHistory)
    },
    setSwitchAllowed({ commit }, switchAllowed) {
      commit('SET_SWITCH_ALLOWED', switchAllowed)
    },
  },
})
