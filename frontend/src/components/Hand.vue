<template>
  <div>
    <p hidden>{{ roundTurn }}</p>
    <span>
      <span
        v-for="(deckSuits, suitIndex) in deck"
        :key="suitIndex"
        :deckSuits="deckSuits"
      >
        <span>
          <img
            class="card"
            :class="turnCSS"
            v-for="(suitCard, cardIndex) in deckSuits.suitCards"
            :key="cardIndex"
            @click="
              phase === 3
                ? compareCards(
                    comparator,
                    createComparedCard(
                      owner,
                      deckSuits.suit,
                      suitCard,
                      suitIndex,
                      cardIndex
                    )
                  )
                : null
            "
            v-bind:src="getImagePath(deckSuits.suit, suitCard)"
            alt="Card in the hand"
          />
        </span>
      </span>
    </span>
  </div>
</template>

<script>
import DebertsService from '@/services/DebertsService.js'
import imageMixin from '@/mixins/imageMixin'
import gsap from 'gsap'

export default {
  name: 'Hand',
  mixins: [imageMixin],
  data() {
    return {
      turnCSS: '',
    }
  },
  props: {
    cards: {
      type: Number,
      required: true,
    },
    comparator: {
      type: Object,
    },
    deck: {
      required: true,
    },
    owner: {
      type: String,
      required: true,
    },
    phase: {
      type: Number,
      required: true,
    },
    roundTurn: {
      required: true,
    },
  },
  mounted() {
    this.setShadowOutlineOnTurn(this.roundTurn)
    gsap.to('.bot', {
      duration: 0.5,
      opacity: 1,
      ease: 'easeOutElastic',
      stagger: {
        each: 0.1,
        from: 0,
      },
    })
    gsap.to('.player', {
      duration: 0.5,
      opacity: 1,
      ease: 'easeOutElastic',
      stagger: {
        each: 0.1,
        from: 9,
      },
    })
  },
  methods: {
    setShadowOutlineOnTurn(roundTurn) {
      roundTurn === this.owner
        ? (this.turnCSS = 'marked ' + this.owner)
        : (this.turnCSS = '')
    },
    compareCards(comparator, comparedCard) {
      if (
        comparator.attacker == null &&
        comparedCard.owner === this.roundTurn
      ) {
        this.setAttacker(comparator, comparedCard)
        this.$emit('update:turn')
        this.setShadowOutlineOnTurn(this.roundTurn)
      } else if (
        comparator.attacker.owner !== comparedCard.owner &&
        comparator.defender == null
      ) {
        this.setDefender(comparator, comparedCard)
        this.setShadowOutlineOnTurn(this.roundTurn)
        setTimeout(() => {
          DebertsService.compareCards(comparator).then((response) => {
            if (response.status === 200) {
              this.$store.dispatch('comparator/removeCards')
              this.$emit('update:score')
            }
          })
        }, 1000)
      }
    },
    createComparedCard(name, suit, card, suitID, cardID) {
      return {
        owner: name,
        card: {
          suit: suit,
          value: card,
        },
        suitID: suitID,
        cardID: cardID,
      }
    },
    setAttacker(comparator, comparedCard) {
      this.$store.dispatch('comparator/setAttacker', comparedCard)
      if (comparator.attacker.owner === 'player') {
        this.$store.dispatch('player/removeCard', comparator.attacker)
      } else {
        this.$store.dispatch('bot/removeCard', comparator.attacker)
      }
    },
    setDefender(comparator, comparedCard) {
      this.$store.dispatch('comparator/setDefender', comparedCard)
      if (comparator.defender.owner === 'player') {
        this.$store.dispatch('player/removeCard', comparator.defender)
      } else {
        this.$store.dispatch('bot/removeCard', comparator.defender)
      }
    },
  },
  updated() {
    this.setShadowOutlineOnTurn(this.roundTurn)
    gsap.to('.bot', {
      duration: 0.5,
      opacity: 1,
      ease: 'easeOutElastic',
      stagger: {
        each: 0.1,
        from: 0,
      },
    })
    gsap.to('.player', {
      duration: 0.5,
      opacity: 1,
      ease: 'easeOutElastic',
      stagger: {
        each: 0.1,
        from: 9,
      },
    })
  },
}
</script>

<style>
.marked:hover {
  box-shadow: 1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue;
}

.player:hover {
  top: -20px;
}

.bot:hover {
  top: 20px;
}
</style>
