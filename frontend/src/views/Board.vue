<template>
  <div>
    <Deck
      v-on:update:partyService="updateParty"
      :bot="bot"
      :deckCards="deckCards"
      :player="player"
      :trump="trump"
      :phase="phase"
      :switchAllowed="switchAllowed"
    ></Deck>
    <div v-if="deckCards < 32">
      <Hand
        class="bot-deck"
        v-on:update:score="updateScore"
        v-on:update:turn="updateTurn"
        :cards="deckCards"
        :comparator="comparator"
        :deck="bot.deck"
        :owner="'bot'"
        :roundTurn="roundTurn"
        :phase="phase"
      ></Hand>
      <Hand
        class="player-deck"
        v-on:update:score="updateScore"
        v-on:update:turn="updateTurn"
        :deck="player.deck"
        :cards="deckCards"
        :comparator="comparator"
        :owner="'player'"
        :roundTurn="roundTurn"
        :phase="phase"
      ></Hand>
    </div>
    <NavbarTop
      class="navbar"
      v-on:update:partyService="updateParty"
      v-on:update:turn="updateTurn"
      :bot="bot"
      :passes="passes"
      :phase="phase"
      :player="player"
      :roundTurn="roundTurn"
      :trumpSuit="trump.suit"
    ></NavbarTop>
    <NavbarBottom
      class="navbar"
      v-on:update:partyService="updateParty"
      v-on:update:turn="updateTurn"
      :passes="passes"
      :phase="phase"
      :player="player"
      :roundHistory="roundHistory"
      :roundTurn="roundTurn"
      :trumpSuit="trump.suit"
    ></NavbarBottom>
  </div>
</template>

<script>
import DebertsService from '@/services/DebertsService.js'
import Deck from '@/components/Deck.vue'
import Hand from '@/components/Hand'
import NavbarBottom from '@/components/NavbarBottom'
import NavbarTop from '@/components/NavbarTop'
import { mapState } from 'vuex'

export default {
  name: 'Board',
  components: {
    Deck,
    Hand,
    NavbarBottom,
    NavbarTop,
  },
  computed: {
    ...mapState([
      'bot',
      'comparator',
      'deckCards',
      'passes',
      'phase',
      'player',
      'roundHistory',
      'roundTurn',
      'switchAllowed',
      'trump',
    ]),
  },
  methods: {
    beforeEnter(el) {
      el.style.opacity = '0'
      el.style.width = '0em'
    },
    enter(el, done) {
      Velocity(
        el,
        { opacity: 1, width: '12em' },
        { duration: 1000, easing: [600, 50], complete: done }
      )
    },
    leave(el, done) {
      Velocity(
        el,
        { opacity: 0, width: '0em' },
        { duration: 500, easing: 'easeInCubic', complete: done }
      )
    },
    updateParty() {
      this.$store.dispatch('player/setDeck', null)
      this.$store.dispatch('bot/setDeck', null)
      DebertsService.getParty().then((response) => {
        this.$store.dispatch('setRoundHistory', response.data.ROUND_HISTORY)
        this.$store.dispatch('setRoundTurn', response.data.TURN.TURN)
        this.$store.dispatch('setDeckCards', response.data.CARD_DECK.CARDS)
        this.$store.dispatch('setPhase', response.data.PHASE)
        this.$store.dispatch('setSwitchAllowed', response.data.SWITCH_ALLOWED)
        this.$store.dispatch('trump/setTrump', response.data.trump.CARD_DECK)
        this.$store.dispatch('bot/setDeck', response.data.bot.CARD_DECK)
        this.$store.dispatch('bot/addCards', response.data.bot.CARDS)
        this.$store.dispatch('player/setDeck', response.data.player.CARD_DECK)
        this.$store.dispatch('player/addCards', response.data.player.CARDS)
        this.$store.dispatch('player/addScore', response.data.TURN.PARTY.player)
        this.$store.dispatch(
          'player/addPoints',
          response.data.TURN.ROUND.player
        )
        this.$store.dispatch('bot/addScore', response.data.TURN.PARTY.bot)
        this.$store.dispatch('bot/addPoints', response.data.TURN.ROUND.bot)
      })
    },
    updateScore() {
      DebertsService.getTurn().then((response) => {
        this.$store.dispatch('player/addScore', response.data.PARTY.player)
        this.$store.dispatch('player/addPoints', response.data.ROUND.player)
        this.$store.dispatch('bot/addScore', response.data.PARTY.bot)
        this.$store.dispatch('bot/addPoints', response.data.ROUND.bot)
        this.$store.dispatch('setRoundTurn', response.data.TURN)
      })
    },
    updateTurn() {
      if (this.roundTurn === 'player') {
        this.$store.dispatch('setRoundTurn', 'bot')
      } else if (this.roundTurn === 'bot') {
        this.$store.dispatch('setRoundTurn', 'player')
      }
    },
  },
}
</script>

<style>
.bot-deck {
  position: absolute;
  bottom: 50px;
}

.player-deck {
  position: absolute;
  top: 55px;
}

.navbar {
  margin-left: -8px;
}
</style>
