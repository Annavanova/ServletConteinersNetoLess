package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private PostService service;
  private PostRepository repository;

  private String getMethod = "GET";
  private String postMethod = "POST";
  private String deleteMethod = "DELETE";
  private String pathEquals = "/api/posts";
  private String pathEqualsReadingAll = "/api/posts/?$";
  private String pathEqualsRemove = "/api/posts/\\d+";
  private String pathLastIndexOf = "/";

  @Override
  public void init() {
    final var context = new AnnotationConfigApplicationContext("ru.netology");
    repository = context.getBean(PostRepository.class);
    service = context.getBean(PostService.class);
    controller = context.getBean(PostController.class);
     }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals(getMethod) && path.equals("/api/posts")) {
        controller.all(resp);
        return;
      }
      //String pathEnd = pathEquals+pathEqualsReadingAll;
      if (method.equals(getMethod) && path.matches("/api/posts/\\d+")) {
        // easy way
        //final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        controller.getById(parseId(path), resp);
        return;
      }
      if (method.equals(postMethod) && path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(deleteMethod) && path.matches("/api/posts/\\d+")) {
        // easy way
      /*  final var id = parseId(path);
        controller.removeById(id, resp);
        return;*/

        //final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        controller.removeById(parseId(path), resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
    }
    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }

  private long parseId(String path) {
    return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
  }
  //private PostController controller;

}

