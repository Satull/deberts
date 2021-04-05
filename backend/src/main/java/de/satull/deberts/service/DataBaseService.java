package de.satull.deberts.service;

import de.satull.deberts.model.db.Party;
import de.satull.deberts.model.db.Player;
import de.satull.deberts.model.db.Round;
import de.satull.deberts.repository.PartyRepository;
import de.satull.deberts.repository.PlayerRepository;
import de.satull.deberts.repository.RoundRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataBaseService {

  @Autowired private PlayerRepository playerRepository;

  @Autowired private PartyRepository partyRepository;

  @Autowired private RoundRepository roundRepository;

  public List<Player> listPlayers() {
    return (List<Player>) playerRepository.findAll();
  }

  public List<Party> listParties() {
    return (List<Party>) partyRepository.findAll();
  }

  public List<Round> listRounds() {
    return (List<Round>) roundRepository.findAll();
  }

  public void save(Round round) {
    playerRepository.save(round.getPlayer());
    partyRepository.save(round.getParty());
    roundRepository.save(round);
  }
}
