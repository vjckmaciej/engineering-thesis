import './App.css';
import { useState } from 'react';

const Home = () => {
  const [blogs, setBlogs ] = useState([
    {title: 'My new website', body: 'Lorem ipsum...', author: 'Mario', id: 1},
    {title: 'Welcome party!', body: 'Lorem ipsum!', author: 'Mario2', id: 2}
  ])

  return (
    <div className="home">
      {blogs.map((blog) => (
        <div className="blog-preview" key={blog.id}>
          <h2>{ blog.title }</h2>
          <p>Written by { blog.author }</p>
        </div>
      ))}
    </div>
  );
}

export default Home;
