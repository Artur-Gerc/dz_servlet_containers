package ru.netology.dz_netol_with_tomcat.servlet.service;


import ru.netology.dz_netol_with_tomcat.servlet.model.Post;
import ru.netology.dz_netol_with_tomcat.servlet.repository.PostRepository;
import ru.netology.dz_netol_with_tomcat.servlet.exception.NotFoundException;

import java.util.List;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
