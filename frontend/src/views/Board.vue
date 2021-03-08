<template>
  <div>
    <div class="absolute bottom-0 left-0 pl-10 pb-74" display="inline">
      <Deck
        v-on:update:party="updateParty"
        :bot="bot"
        :deckCards="deckCards"
        :player="player"
        :trump="trump"
        :phase="phase"
        :switchAllowed="switchAllowed"
      ></Deck>
    </div>
    <Comparator
      class="absolute bottom-0 left-0 pb-76 pl-120"
      display="inline"
      :comparator="comparator"
    ></Comparator>
    <div v-if="deckCards < 32">
      <Hand
        class="absolute inset-x-0 pl-8 pt-14 top-0"
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
        class="absolute bottom-0 inset-x-0 pb-14 pl-8"
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
      v-on:update:party="updateParty"
      v-on:update:turn="updateTurn"
      :bot="bot"
      :passes="passes"
      :phase="phase"
      :player="player"
      :roundTurn="roundTurn"
      :trumpSuit="trump.suit"
    ></NavbarTop>
    <NavbarBottom
      v-on:update:party="updateParty"
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
import Comparator from '@/components/Comparator.vue'
import DebertsService from '@/services/DebertsService.js'
import Deck from '@/components/Deck.vue'
import Hand from '@/components/Hand.vue'
import NavbarBottom from '@/components/NavbarBottom.vue'
import NavbarTop from '@/components/NavbarTop.vue'
import { mapState } from 'vuex'

export default {
  name: 'Board',
  components: { Deck, NavbarBottom, NavbarTop, Hand, Comparator },
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
      'trump'
    ])
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
      DebertsService.getParty().then(response => {
        this.$store.dispatch('setRoundHistory', response.data.ROUND_HISTORY)
        this.$store.dispatch('setRoundTurn', response.data.TURN.TURN)
        this.$store.dispatch('setDeckCards', response.data.CARD_DECK.CARDS)
        this.$store.dispatch('setPhase', response.data.PHASE)
        this.$store.dispatch('setSwitchAllowed', response.data.SWITCH_ALLOWED)
        this.$store.dispatch('trump/setTrump', response.data.TRUMP.CARD_DECK)
        this.$store.dispatch('bot/setDeck', response.data.BOT.CARD_DECK)
        this.$store.dispatch('bot/addCards', response.data.BOT.CARDS)
        this.$store.dispatch('player/setDeck', response.data.PLAYER.CARD_DECK)
        this.$store.dispatch('player/addCards', response.data.PLAYER.CARDS)
        this.$store.dispatch('player/addScore', response.data.TURN.PARTY.PLAYER)
        this.$store.dispatch(
          'player/addPoints',
          response.data.TURN.ROUND.PLAYER
        )
        this.$store.dispatch('bot/addScore', response.data.TURN.PARTY.BOT)
        this.$store.dispatch('bot/addPoints', response.data.TURN.ROUND.BOT)
      })
    },
    updateScore() {
      DebertsService.getTurn().then(response => {
        this.$store.dispatch('player/addScore', response.data.party.player)
        this.$store.dispatch('player/addPoints', response.data.round.player)
        this.$store.dispatch('bot/addScore', response.data.party.bot)
        this.$store.dispatch('bot/addPoints', response.data.round.bot)
        this.$store.dispatch('setRoundTurn', response.data.turn)
      })
    },
    updateTurn() {
      if (this.roundTurn === 'player') {
        this.$store.dispatch('setRoundTurn', 'bot')
      } else if (this.roundTurn === 'bot') {
        this.$store.dispatch('setRoundTurn', 'player')
      }
    }
  }
}
</script>
