package com.blog.springblog.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.blog.springblog.repository.BlogRepository;
import com.blog.springblog.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class BlogController {
	
	@Autowired
	BlogRepository blogRepo;
	
	@GetMapping("/index")
	public String index() {
		return "Welcome to Blog Management Portal";
	}
	
	@GetMapping("/about")
	public String blogInfo() {
		return "This is a portal to manage Blog data and their respective blog posts";
		
	}
	
	@GetMapping("/blogs")
	public List<Blog> getAllBlogs() {
		return blogRepo.findAll();
	}
	
	@PostMapping("/blogs")
	public Blog createBlog(@Valid @RequestBody Blog blog) {
		return blogRepo.save(blog);
	}
	
	@GetMapping("/blogs/{id}")
	public Blog getBlogById(@PathVariable(value = "id") Long blogId) {
		return blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found with this ID"));
	}
	
	@PutMapping("/blogs/{id}")
	public Blog updateBlog(@PathVariable(value = "id") Long blogId, 
							@Valid @RequestBody Blog blog) {
		Blog obj = blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found with given ID"));
		
		obj.setTitle(blog.getTitle());
		obj.setOwner(blog.getOwner());
		obj.setDescription(blog.getDescription());
		
		Blog updateBlog = blogRepo.save(obj);
		return updateBlog;
	}
	
	@DeleteMapping("/blogs/{id}")
	public ResponseEntity<?> deleteBlog(@PathVariable(value = "id") Long blogId) {
		
		Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found with given ID"));
		blogRepo.delete(blog);
		return ResponseEntity.ok().build();
	}
	

}
