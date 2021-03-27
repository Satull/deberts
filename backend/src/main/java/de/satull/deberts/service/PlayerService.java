package de.satull.deberts.service;

import de.satull.deberts.model.db.Players;
import de.satull.deberts.repository.PlayerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Players> list() {
      return (List<Players>) playerRepository.findAll();
    }

}
