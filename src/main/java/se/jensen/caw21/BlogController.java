package se.jensen.caw21;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "api/v1/blog")
public class BlogController {

    ArrayList<Blog> blogList;
    int blogID;

    public BlogController() {
        blogList = new ArrayList<Blog>();
        blogID = 0;
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<Blog> createNewBlog(@RequestBody Blog Nblog) {
        if (Nblog.getTitle() == "") {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {

            blogID++;
            Nblog.setId(blogID);
            blogList.add(Nblog);
            //System.out.println("Lade till en film: " + Nblog.getTitle());
            return new ResponseEntity<Blog>( HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ArrayList<Blog> listBlog() {
        return blogList;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ResponseEntity<Blog> viewBlog(@PathVariable("id") int id) {
        //System.out.println("Getting movie with id " + id);

        Blog fetchedByID = getBlogByID(id);

        if (fetchedByID == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Blog>(fetchedByID, HttpStatus.OK);
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public Blog updateBlog(@PathVariable("id") int id, @RequestBody Blog blogChanges) {
        System.out.println("update blog with id " + id);
        System.out.println("Updated blog entry : " + blogChanges);
        Blog blogToUpdate = getBlogByID(id);
        System.out.println("Old blog entry : " + blogToUpdate);

        if (blogChanges.getTitle() != null) {
            blogToUpdate.setTitle(blogChanges.getTitle());
            System.out.println("The update title is : " + blogToUpdate.getTitle());
        }
        if (blogChanges.getContent() != null) {
            blogToUpdate.setContent(blogChanges.getContent());
            System.out.println("The updated content is : "+ blogToUpdate.getContent());
        }

        updateBlogByID( id, blogToUpdate);
        return blogToUpdate;
    }

    public Blog updateBlogByID(int id, Blog updatedBlog) {
        for (int i = 0; i < blogList.size(); i++) {
            Blog currentBlog = blogList.get(i);
            if (currentBlog.getId() == id) {
                blogList.set(i, updatedBlog);
                return blogList.get(i);
            }
        }

        return new Blog();
    }


    public Blog getBlogByID(int id) {
        for (int i = 0; i < blogList.size(); i++) {
            Blog currentBlog = blogList.get(i);
            if (currentBlog.getId() == id) {
                return blogList.get(i);
            }
        }

        return null;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Blog deleteBlogByID(@PathVariable("id") int id) {
        for (int i = 0; i < blogList.size(); i++) {
          Blog currentBlog=blogList.get(i);
          if(currentBlog.getId()==id){
              blogList.remove(i);
          }
        }
        return null;

    }
}

