import React, {useEffect, useState} from 'react'
import {Link, useLocation} from "react-router-dom";
import {Button} from "@mui/material";
import MovieCard from "../components/MovieCard.jsx";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

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
                console.error(response.title);
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
    }, [title]);

    const handleClick = () => {
        console.log("Hello")
    }

    return (
        <>
            <Button component={Link} to='/' disableElevation startIcon={<ArrowBackIcon />} sx={{color: '#646CFF', position: 'absolute', left: 16, top: 16, "&:hover": {
                    color: '#8086FF', backgroundColor: 'white'
                }}} onClick={handleClick}>
                Home
            </Button>
            <MovieCard title={movieData.title} year={"test"} director={"test"} rating={"test"} stars={"test"} genre={"test"}/>
            {/*<MovieCard title={movieData.title} year={movieData.year} director={movieData.director} rating={movieData.rating} stars={movieData.stars} genre={movieData.genre}/>*/}
            {/*<h1>{movieData.title}</h1>*/}
            {/*<div>YEAR: {movieData.year}</div>*/}
            {/*<div>DIRECTOR: {movieData.director}</div>*/}
            {/*<div>GENRES: {movieData.genres.join(', ')}</div> {}*/}
            {/*<div>STARS:*/}
            {/*    {movieData.stars.map((star, index) => (*/}
            {/*        <React.Fragment key={star}>*/}
            {/*            {index > 0 && ', '}*/}
            {/*            <Link to={`/singlestar?name=${encodeURIComponent(star)}`}>{star}</Link>*/}
            {/*        </React.Fragment>*/}
            {/*    ))}*/}
            {/*</div>*/}
        </>
    );

}
