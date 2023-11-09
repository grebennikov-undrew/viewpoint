import * as React from 'react';
import { LineChart } from '@mui/x-charts/LineChart';
import { Box, height, width } from '@mui/system';
import dayjs from 'dayjs';

const legendPlacement = {
    slotProps: {
      legend: {
        position: {
          vertical: 'top',
          horizontal: 'middle',
        },
        direction: 'row',
        itemGap: 20,
      },
    },
    margin: {
        bottom: 50,
    },
  };

const LineArea = ({chartData, chartResult}) => {
    if (!chartResult || !chartData) return;

    const { rows, columns, data } = chartResult;
    const { chartSettings, dataset } = chartData;
    const { metrics, dimensions, xAxis } = chartSettings;

    // Устаноить тип оси
    const sortedKeys = columns.sort();
    const xColumn = dataset.columns.find(c => c.name == xAxis);
    let xAxisSettings;
    if (xColumn["type"] === "Timestamp") {
        xAxisSettings = {
            data: sortedKeys.map(stringDate => new Date(stringDate)),
            scaleType: 'time',
            tickMinStep: 3600 * 1000 * 24, // 24 h
            valueFormatter: (value) => (dayjs(value).format("YYYY-MM-DD")),
        }
    }

    const metric = metrics[0];

    const seriesSettings = Object.keys(data).map(rowKey => ({
        id: rowKey,
        label: Object.keys(data).length > 1 ? rowKey : metric.label,
        data: sortedKeys.map(date => data[rowKey][date])
    }))

    // const seriesData = Object.keys(data).map(key => {
    //     const row = data[key];
    //     let label = "";
    //     dimensions.map((c, idx) => {
    //         label += row[c.label] + ", "; 
    //     })
    //     label = label.substring(0, label.length - 2)
    //     return { id: key, value: row[metric.label], label: label}
    // })
    
    return (
        <LineChart
            xAxis={[{ ...xAxisSettings }]}
            series={[
                ...seriesSettings
            ]}
            margin={{ top: 10, bottom: 20 }}
            {...legendPlacement}
        />
    )
}


// const valueFormatter = (date) =>
//     date.toLocaleDateString('ru-RU', {
//         year: '2-digit',
//         month: '2-digit',
//         day: '2-digit',
//     });

export default LineArea;