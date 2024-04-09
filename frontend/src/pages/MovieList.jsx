import {useEffect, useState} from 'react'

function MovieList() {
    const [movieData, setMovieData] = useState(null);


    useEffect(() => {
        let mounted = true;
        async function fetchMovieData(){
            try{
                const response = await fetch('http://localhost:8080/fabFlix_war/movielist');

                if (!response.ok) {
                    console.error('response is not status 200');
                }

                const data = await response.json();

                if (mounted){
                    setMovieData(data);
                    console.log(data);
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





    return (
        <>
            <div>
                hello world
            </div>
            {movieData? (
                <ul>
                    {movieData.map((elem) => {
                        return <li key={elem.title}>{elem.title}</li>
                    })}
                </ul>
                ) : (
                    <div>loading...</div>
            )}

        </>
    )
}

export default MovieList
