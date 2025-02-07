package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Stub
public class PostRepository {
  /*public List<Post> all() {
    return Collections.emptyList();
  }

  public Optional<Post> getById(long id) {
    return Optional.empty();
  }

  public Post save(Post post) {
    return post;
  }

  public void removeById(long id) {
  }*/
  private final List<Post> posts = new ArrayList<>();
  private int postCount;

  public PostRepository() {
    posts.addAll(List.of(
            new Post(1, "First"),
            new Post(2, "Second"),
            new Post(3, "Third"),
            new Post(4, "Fourth")
    ));
    postCount = 4;
  }

  public List<Post> all() {
    return posts;
  }

  public synchronized Optional<Post> getById(long id) {
    return posts.stream().filter(post -> post.getId() == id).findFirst();
  }

  public synchronized Post save(Post post) {
    Optional<Post> lookForPost = posts.stream().filter(p -> p.getId() == post.getId()).findFirst();
    if (post.getId() == 0 || lookForPost.isEmpty()) {
      post.setId(++postCount);
      posts.add(post);
      postCount = posts.size();
    }
    return post;
  }

  public synchronized void removeById(long id) {
    for (Post p : posts) {
      if (p.getId() == id) {
        posts.remove(p);
        postCount = posts.size();
        break;
      }
    }
  }
}
