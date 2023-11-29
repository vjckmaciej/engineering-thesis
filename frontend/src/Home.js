import './App.css';
import BlogList from './BlogList'
import useFetch from './useFetch';
import Navbar from './Navbar';

const Home = () => {
  const { data: blogs, isPending, error } = useFetch('http://localhost:8000/blogs');

  return (
    <div className="home">
      { error && <div>{ error} </div> }
      { isPending && <div>Loading...</div>}
      {blogs && <BlogList blogs = { blogs } title = "Luigi"/> }
    </div>
  );
}

export default Home;
