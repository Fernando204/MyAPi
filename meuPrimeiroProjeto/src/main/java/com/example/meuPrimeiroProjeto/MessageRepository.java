package com.example.meuPrimeiroProjeto;

import com.example.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages,Long>{
    List<Messages> findBySaidaContainingOrDestinoContaining(String saida, String destino);
}
