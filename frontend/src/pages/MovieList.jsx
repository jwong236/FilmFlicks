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
            <h1 className="text-2xl font-bold mb-4">MOVIE LIST</h1>
            {movieData ? (
                <table className="border border-collapse border-black">
                    <thead>
                    <tr className="bg-blue-600 text-white">
                        <th className="border border-black px-4 py-2">Title</th>
                        <th className="border border-black px-4 py-2">Year</th>
                        <th className="border border-black px-4 py-2">Director</th>
                        <th className="border border-black px-4 py-2">Genres</th>
                        <th className="border border-black px-4 py-2">Stars</th>
                        <th className="border border-black px-4 py-2">Rating</th>
                    </tr>
                    </thead>
                    <tbody>
                        {movieData.map((elem, index) => {
                            const bgColor = index % 2 === 0 ? "bg-gray-100" : "bg-white";
                            return (
                                <tr key={index} className={bgColor}>
                                    <td className="border border-black px-4 py-2">{elem.title}</td>
                                    <td className="border border-black px-4 py-2">{elem.year}</td>
                                    <td className="border border-black px-4 py-2">{elem.director}</td>
                                    <td className="border border-black px-4 py-2">{elem.genres}</td>
                                    <td className="border border-black px-4 py-2">{elem.stars}</td>
                                    <td className="border border-black px-4 py-2">{elem.rating}</td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            ) : (
                <div>loading...</div>
            )}
        </>
    );

}

export default MovieList
