import {useEffect, useState} from 'react'
import {Link, useLocation} from "react-router-dom";

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


    // console.log("name " + starData.name);
    // console.log("birthyear " + starData.birthYear);
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

        <>
            <div>
                <Link to={"/"}>
                    HOME
                </Link>
            </div>
            <h1>
                STAR
            </h1>

            <div>NAME: {starData.name}</div>
            <div>YEAR: {birthYear}</div>
            {/*<div>MOVIES: {starData.movieTitles}</div>*/}
            <div>
                {movieArr.map((elem, index) => {
                    return (
                        <Link key = {index} to={"/"}> {elem}</Link>
                    )
                })}
            </div>
        </>
    );

}

export default SingleStar
