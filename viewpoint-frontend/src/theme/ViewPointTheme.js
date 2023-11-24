import * as React from 'react';
import { createTheme, responsiveFontSizes, Theme } from '@mui/material/styles';

const typography = {
    h1: {
        fontSize: 28,
        marginTop: 12,
        marginBottom: 12,
    },
    h2: {
        fontSize: 24,
        marginTop: 10,
        marginBottom: 10,
    },
    h3: {
        fontSize: 20,
        marginTop: 8,
        marginBottom: 8,
    },
    h4: {
        fontSize: 17,
        marginTop: 6,
        marginBottom: 6,
    },
    h5: {
        fontSize: 15,
        marginTop: 5,
        marginBottom: 5,
    },
    h6: {
        fontSize: 12,
        marginTop: 3,
        marginBottom: 2,
    },
    allVariants: {
        color: "#52525B",
        fontWeight: 400,
    },
    fontFamily: `"Inter", "Roboto", "Helvetica", "Arial", sans-serif`,
};

export const ViewPointTheme = responsiveFontSizes(createTheme({
    breakpoints: {
        values: {
            xs: 320,
            sm: 600,
            md: 960,
            lg: 1280,
            xl: 1920,
        }
    },
    spacing: 4,
    typography: typography,
}));

