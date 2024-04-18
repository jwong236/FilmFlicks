import {Box, Card, CardHeader, CardContent, Chip, Typography, Link as MuiLink} from '@mui/material'
import { Link as RouterLink } from "react-router-dom";
export default function MovieCard(props) { // title, year, director, genres, stars, rating
    const bull = (
        <Box
            component="span"
            sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
        >
            •
        </Box>
    );

    return (
        <Card sx={{ width: '100%', height: '80vh', backgroundColor: 'secondary.main', borderRadius: '1rem', display: 'flex', flexDirection: 'column',  justifyContent: 'flex-end'}}>
            <Box sx={{display: "flex", flexDirection: "column", height: '50%', justifyContent: 'space-around'}}>
                <Box sx={{display: "flex", width: "100%",  justifyContent: 'space-between'}}>
                    <Box sx={{display: "flex", flexDirection: "column",  alignItems: "flex-start", paddingLeft: '1rem', minHeight: '5rem'}}>
                        <CardHeader
                            title={ props.link ?
                                <MuiLink
                                    component={RouterLink}
                                    to={`/singlemovie?title=${encodeURIComponent(props.title)}`}
                                    sx={{
                                        textDecoration: 'none',
                                        color: 'primary.main',
                                        fontWeight: 'bold',
                                        '&:hover': {
                                            color: 'primary.dark',
                                            textDecoration: 'underline',
                                        }
                                    }}
                                >
                                    {props.title}
                                </MuiLink> : props.title
                            }
                            sx={{
                                padding: 0,
                                color: 'primary.main',
                            }}
                        />
                        <CardContent sx={{padding: 0}}>
                            <Typography
                                sx={(theme) => ({
                                    color: 'primary.main',
                                    [theme.breakpoints.only("md")]: {
                                        fontSize:"0.75rem",
                                    },
                                })}>
                                {props.year}{bull}{props.director}
                            </Typography>

                        </CardContent>
                    </Box>
                    <Box sx={{ color: 'primary.main', height: '100%', aspectRatio: 1, borderRadius: '5rem', border: '0.25rem solid #803D33', display: 'flex', alignItems: 'center', justifyContent: 'center', marginRight: '1rem'}}>{props.rating}</Box>
                </Box>
                <Box sx={{ display: "flex", maxWidth: "90%", padding: '0 1rem', flexWrap: 'wrap', gap: '8px' }}>
                    {props.stars.map((star, index) => (
                        <Chip
                            key={index}
                            label={
                                <MuiLink
                                    component={RouterLink}
                                    to={`/singlestar?name=${encodeURIComponent(star)}`}
                                    sx={{
                                        textDecoration: 'none',

                                        '&:hover': {
                                            color: 'secondary.dark', // Same color in-case I want to change it later
                                            textDecoration: 'none'
                                        }
                                    }}
                                >
                                    {star}
                                </MuiLink>
                            }
                            sx={{
                                backgroundColor: 'secondary.dark',
                                "&:hover" : {
                                    backgroundColor: 'primary.main',
                                    transition: '0.3s'
                                }
                            }}
                        />
                    ))}
                </Box>
                <Box sx={{ display: "flex", width: "100%", paddingLeft: '1rem', flexWrap: "wrap" }}>
                    {props.genres.map((genre, index) => (
                        <Chip
                            key={index}
                            label={`#${genre}`}
                            sx={{ color: 'primary.main', backgroundColor: 'primary.light', marginRight: '8px', marginBottom: '8px' }}
                        />
                    ))}
                </Box>
            </Box>

        </Card>
    )
}