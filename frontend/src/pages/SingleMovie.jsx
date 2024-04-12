import React, {useEffect, useState} from 'react'
import {Link, useLocation} from "react-router-dom";
import {Button} from "@mui/material";

export default function SingleMovie() {
    const [movieData, setMovieData] = useState({
        title: "default title",
        year: "default year",
        director: "default director",
        genres: ["genre1"],
        stars: ["star1"]

    });

    const location = useLocation();
    const search_params = new URLSearchParams(location.search);
    const title = search_params.get('title');

    useEffect(() => {
        let mounted = true;
        async function fetchMovieData(){
            try{
                const response = await fetch(`http://localhost:8080/fabFlix_war/singlemovie?title=${encodeURIComponent(title)}`);

                if (!response.ok) {
                    console.error('response is not status 200');
                }
                const data = await response.json();
                if (mounted){
                    setMovieData(data[0]);
                }
            }catch(error){
                console.error('Error fetching data: ', error);
            }
        }

        fetchMovieData();

        return ()=>{
            mounted = false;
        }
    }, []);

    const handleClick = () => {
        console.log("Hello")
    }

    return (
        <>
            <Button component={Link} to='/' variant='contained' disableElevation sx={{backgroundColor: '#646CFF', "&:hover": {
                    backgroundColor: 'pink'
                }}}onClick={handleClick}>
                Home
            </Button>
            <h1>{movieData.title}</h1>
            <div>YEAR: {movieData.year}</div>
            <div>DIRECTOR: {movieData.director}</div>
            <div>GENRES: {movieData.genres.join(', ')}</div> {}
            <div>STARS:
                {movieData.stars.map((star, index) => (
                    <React.Fragment key={star}>
                        {index > 0 && ', '}
                        <Link to={`/singlestar?name=${encodeURIComponent(star)}`}>{star}</Link>
                    </React.Fragment>
                ))}
            </div>
        </>
    );

}
