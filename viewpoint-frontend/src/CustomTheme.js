import * as React from 'react';
import { createTheme, responsiveFontSizes } from '@mui/material/styles';

// Создаем новую тему
const theme = createTheme();

// Добавляем свои собственные брейкпоинты
const customBreakpoints = {
  xs: 320, // Ваш кастомный брейкпоинт
  sm: 600, // Ваш кастомный брейкпоинт
  md: 960, // Ваш кастомный брейкпоинт
  lg: 1280, // Ваш кастомный брейкпоинт
  xl: 1920, // Ваш кастомный брейкпоинт
};

const customTypography = {
    h1: { fontSize: 10, },
}

// Обновляем брейкпоинты в теме
theme.breakpoints.values = { ...theme.breakpoints.values, ...customBreakpoints };
theme.typography.values = { ...theme.typography.values, ...customTypography };
theme.typography.h1.fontSize = 40;

theme.typography.h2.fontSize = 28;

theme.typography.h2.paddingTop = 10;
theme.typography.h2.paddingBottom = 10;
// theme.typography.h2.marginTop = 20;

theme.typography.h3.fontSize = 24;
theme.typography.h3.fontSize = 20;

// Создаем результирующую тему с новыми брейкпоинтами и, если нужно, с адаптивными размерами шрифтов
const customTheme = responsiveFontSizes(theme);

export default customTheme;