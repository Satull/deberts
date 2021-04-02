package de.satull.deberts.service;

import de.satull.deberts.model.db.Party;
import de.satull.deberts.model.db.Player;
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

  public List<Player> list() {
    return (List<Player>) playerRepository.findAll();
  }

  public void save(Party party) {
    playerRepository.save(party.getPlayer());
    partyRepository.save(party);
  }
}
