import { useEffect, useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  CartesianGrid,
  Legend,
} from "recharts";
import axios from "axios";

interface SolarData {
  timestamp: string;
  production: number;
  consumption: number;
  batteryLevel: number;
  gridImport: number;
}

export default function Dashboard() {
  const [data, setData] = useState<SolarData[]>([]);

  useEffect(() => {
    axios.get<SolarData[]>("http://46.13.167.200:30400/api/solar-data").then((res) => {
      setData(res.data);
    });
  }, []);

  const getLineColor = (type: string): string => {
    switch (type) {
      case "production":
        return "#FFD700"; // yellow
      case "consumption":
        return "#1E90FF"; // blue
      case "batteryLevel":
        return "#FFA500"; // orange
      case "gridImport":
        return "#DA70D6"; // orchid
      default:
        return "#8884d8";
    }
  };

  const renderChart = (type: keyof SolarData) => (
    <div className="w-full md:w-1/2 p-4">
      <div className="bg-zinc-800 rounded-xl p-4 shadow">
        <h2 className="text-xl font-semibold mb-2 text-center capitalize">{type}</h2>
        <ResponsiveContainer width="100%" height={300}>
          <LineChart data={data} margin={{ top: 20, right: 30, left: 20, bottom: 10 }}>
            <CartesianGrid strokeDasharray="3 3" stroke="#444" />
            <XAxis dataKey="timestamp" tick={{ fill: "#ccc" }} />
            <YAxis tick={{ fill: "#ccc" }} />
            <Tooltip contentStyle={{ backgroundColor: "#333", borderColor: "#666" }} />
            <Legend />
            <Line
              type="monotone"
              dataKey={type}
              stroke={getLineColor(type)}
              strokeWidth={3}
              dot={{ r: 1 }}
            />
          </LineChart>
        </ResponsiveContainer>
      </div>
    </div>
  );

  return (
    <div className="min-h-screen bg-zinc-900 text-white p-6">
      <h1 className="text-3xl font-bold mb-6 text-center">🌞 Solar Dashboard</h1>
      <div className="flex flex-wrap -m-4">
        {renderChart("production")}
        {renderChart("consumption")}
        {renderChart("batteryLevel")}
        {renderChart("gridImport")}
      </div>
    </div>
  );
}
