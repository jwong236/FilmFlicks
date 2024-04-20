import createTheme from "@mui/material/styles/createTheme";

const colorTheme = createTheme({
    palette: {
        primary: {
            dark: '#4D0A00', // darker red
            main: "#803D33", // dark red
            light: '#E5BEB8' // light red
        },
        secondary: {
            dark: '#E5D9B8',
            main: '#F8E6AE',
            light: '#F8EFD3',
        },
        info: {
            dark: '#646CFF',
            main: '#8086FF',
            light: '#BFC3FF',
        }
    }
})

const theme = createTheme(colorTheme, {
    components: {
        MuiFormControl:{
            styleOverrides:{
                root: {
                    '& .MuiInputBase-root': {
                        height: '2rem',
                        backgroundColor: '#f6f6f6',
                        '& input': {
                            height: '100%'
                        },
                        '&:hover fieldset': {
                            borderColor: colorTheme.palette.info.main,
                        },
                        '&.Mui-focused fieldset': {
                            borderColor: colorTheme.palette.info.dark,
                        }
                    }
                }
            }
        },
        MuiChip: {
            styleOverrides: {
                root: {
                    "&.MuiChip-root:hover": {
                        "& .MuiTypography-root": {
                            color: colorTheme.palette.secondary.dark,
                        },
                        "&. MuiLink-root": {
                            color: colorTheme.palette.secondary.dark,
                        }
                    }
                }
            }
        },
        MuiCardContent: {
            styleOverrides: {
                root: {
                    "&:last-child": {
                       padding: 0,
                    }
                }
            }
        }
    }
})

export default theme;