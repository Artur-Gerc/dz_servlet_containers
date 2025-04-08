package ru.netology.dz_netol_with_tomcat.servlet.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.dz_netol_with_tomcat.servlet.controller.PostController;
import ru.netology.dz_netol_with_tomcat.servlet.repository.PostRepository;
import ru.netology.dz_netol_with_tomcat.servlet.service.PostService;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String API_POSTS = "/api/posts";
    private static final String API_POSTS_WITH_ID = "/api/posts/\\d+";


    @Override
    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.netology");
        controller = context.getBean(PostController.class);
        
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            switch (method) {
                case GET:
                    if (path.equals(API_POSTS)) {
                        controller.all(resp);
                        return;
                    } else if (path.matches(API_POSTS_WITH_ID)) {
                        long id = extractPostId(path);
                        controller.getById(id, resp);
                        return;
                    }
                case POST:
                    if (path.equals(API_POSTS)) {
                        controller.save(req.getReader(), resp);
                        return;
                    }
                case DELETE:
                    if (path.matches(API_POSTS)) {
                        long id = extractPostId(path);
                        controller.removeById(id, resp);
                        return;
                    }
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long extractPostId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/")).replace("/", ""));
    }

//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) {
//        // если деплоились в root context, то достаточно этого
//        try {
//            final var path = req.getRequestURI();
//            final var method = req.getMethod();
//            // primitive routing
//            if (method.equals("GET") && path.equals("/api/posts")) {
//                controller.all(resp);
//                return;
//            }
//            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
//                // easy way
//                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
//                controller.getById(id, resp);
//                return;
//            }
//            if (method.equals("POST") && path.equals("/api/posts")) {
//                controller.save(req.getReader(), resp);
//                return;
//            }
//            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
//                // easy way
//                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
//                controller.removeById(id, resp);
//                return;
//            }
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//    }
}