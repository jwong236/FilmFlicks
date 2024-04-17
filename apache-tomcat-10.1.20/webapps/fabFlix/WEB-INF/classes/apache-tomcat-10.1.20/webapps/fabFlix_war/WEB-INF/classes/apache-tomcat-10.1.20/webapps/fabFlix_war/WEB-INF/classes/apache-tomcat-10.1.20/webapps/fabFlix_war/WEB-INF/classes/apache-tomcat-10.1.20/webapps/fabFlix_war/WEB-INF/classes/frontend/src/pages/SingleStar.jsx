import React, {useEffect, useState} from 'react'
import {Link, useLocation} from "react-router-dom";
import HomeButton from "../components/HomeButton.jsx";
import StarCard from "../components/StarCard.jsx";
import {Box}  from '@mui/material'
import popcorn from "../assets/popcorn.png";

function SingleStar() {
    const [starData, setStarData] = useState({
        name: "",
        birthYear: "N/A",
        movieTitles: []

    });

    const location = useLocation();
    const search_params = new URLSearchParams(location.search);
    const name = search_params.get('name');
    //console.log(name);

    useEffect(() => {
        let mounted = true;
        async function fetchStarData(){
            try{
                const response = await fetch(`http://localhost:8080/fabFlix_war/singlestar?name=${encodeURIComponent(name)}`);
                if (!response.ok) {
                    console.error('response is not status 200');
                }
                const data = await response.json();
                if (mounted){
                    setStarData(data[0]);
                }
            }catch(error){
                console.error('Error fetching data: ', error);
            }
        }

        fetchStarData();

        return ()=>{
            mounted = false;
        }
    }, []);
    const birthYear = starData.birthYear === null ? "N/A" : starData.birthYear;
    let movieArr = [];
    if (starData.movieTitles.includes(',')){
        movieArr = starData.movieTitles.split(', ');
        console.log(movieArr);
    }
    else{
        movieArr.push(starData.movieTitles);
        console.log("one movie title");
    }
    return (
        <Box sx={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            width: '100%',
            height: '100vh',
            backgroundColor: '#f1f1f1',
            padding: 3,
            backgroundImage: `url(${popcorn})`,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
        }}>
            <HomeButton/>
            <Box sx={{width: '30%'}}>
                <StarCard
                    name = {starData.name}
                    birthYear = {birthYear}
                    movieTitles = {movieArr}
                />
            </Box>
        </Box>
    );
}

export default SingleStar
