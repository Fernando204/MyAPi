package com.example.meuPrimeiroProjeto;

import com.example.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageRepository messageRepository;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder,MessageRepository messageRepository){
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/GetMessages")
    public ResponseEntity<?> getMessages(@RequestParam String keyword){
        List<Messages> mensagens = messageRepository.findBySaidaContainingOrDestinoContaining(keyword,keyword);
        return mensagens.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :ResponseEntity.ok(mensagens);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/Register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if (userRepository.existsByName(user.getName())) {//verifica se o usuário já existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario Já registrado");
        }

        String encriptedPassword = passwordEncoder.encode(user.getSenha());//criptografa a senha
        user.setSenha(encriptedPassword);

        System.out.println("usuário salvo");
        User savedUser = userRepository.save(user);//salva o user no banco de dados
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);//retorna um "objeto" com o usuário registrado
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Optional<User> userOptional = userRepository.findByName(loginRequest.getName());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("usuario não registrado");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getSenha(), user.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("senha incorreta");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);

    }
    @PostMapping("/mensagem")
    public ResponseEntity<?> postMessages(@RequestBody Messages messages) {
        Optional<User> userOptional = userRepository.findByName(messages.getDestino());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário Destino não existe");
        }
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dataFormat = sdf.format(data);
        
        messages.setData(dataFormat);
        messageRepository.save(messages);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("MENSAGEM ENVIADA");
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
            .map(user -> {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                return ResponseEntity.ok(userRepository.save(user));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
