
import TableSettingsArea from "./Table/TableSettingsArea";
import PieSettings from "./pie/PieSettings";
import LineSettings from "./line/LineSettings";
import BarSettings from "./bar/BarSettings";

const SettingsArea = (props) => {
    const {chartData} = props;

    if (!chartData) return;
    if (chartData.chartType.toLowerCase() === "table") {
        return (<TableSettingsArea {...props}/>);
    } else if (chartData.chartType.toLowerCase() === "pie") {
        return <PieSettings {...props}/>
    } else if (chartData.chartType.toLowerCase() === "line") {
        return <LineSettings {...props}/>
    } else if (chartData.chartType.toLowerCase() === "bar") {
        return <BarSettings {...props}/>
    }
    return;
}

export default SettingsArea;