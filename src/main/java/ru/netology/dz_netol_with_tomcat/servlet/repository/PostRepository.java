package ru.netology.dz_netol_with_tomcat.servlet.repository;

import ru.netology.dz_netol_with_tomcat.servlet.exception.NotFoundException;
import ru.netology.dz_netol_with_tomcat.servlet.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong postId = new AtomicLong(0);


    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if(post.getId() == 0){
            long newId = postId.incrementAndGet();
            post.setId(newId);
            posts.put(newId, post);
        } else {
            if(posts.containsKey(post.getId())){
                posts.put(post.getId(), post);
            } else {
                posts.put(post.getId(), post);
            }
        }
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}