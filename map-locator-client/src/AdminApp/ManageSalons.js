import Map from "vector-maps/dist/Map";
import SearchAndZoom from "vector-maps/dist/features/search/SearchAndZoom";
import React, { useEffect, useState } from "react";
import Menu from "./Menu";
import Markers from "./Markers.js";
import "./adminApp.css";
import { useMap } from "vector-maps/dist/MapContext";
import {
  sUrl,
  bsUrl,
} from "/home/karpowic/Desktop/map-locator-all/map-locator-client/src/Constant.js";

const ManageSalons = () => {
  const [category, setCategory] = useState(null);
  const [salons, setSalons] = useState([]);
  const [bbox, setBbox] = useState(null);
  const [latitude, setLatitude] = useState(0);
  const [longitude, setLongitude] = useState(0);
  const [bounds, setBounds] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    async function load() {
      if (category !== null && bbox === null) {
        const response = await fetch(`${bsUrl}${category}`, {
          method: "POST",
          credentials: "include",
          body: JSON.stringify({
            serviceName: category,
            LatBtm: bounds._ne.lat,
            LonBtm: bounds._sw.lng,
            LatTop: bounds._sw.lat,
            LonTop: bounds._ne.lng,
            distance: 0,
            treatment: null,
            priceFrom: 0,
            priceTo: 0,
            opinion: 0.0,
            localizationLat: latitude,
            localizationLon: longitude,
          }),
          headers: {
            "Content-Type": "application/json",
          },
        });
        let json = [];

        if (response.ok) {
          json = await response.json();
          setSalons(json);
        } else {
          alert("Nie znaleziono rekordow  w bazie danych dla 1 " + category);
        }
        setSalons(json);
        console.log(json);
        console.log("Kategory i bounds");
      }
    }

    load();
  }, [category]);

  useEffect(() => {
    async function load() {
      console.log(bbox);
      if (category !== null && bbox !== null) {
        const response = await fetch(`${sUrl}${category}`, {
          method: "POST",
          credentials: "include",
          body: JSON.stringify({
            serviceName: category,
            LatBtm: bbox.btmRightPoint?.lat,
            LonBtm: bbox.btmRightPoint?.lon,
            LatTop: bbox.topLeftPoint?.lat,
            LonTop: bbox.topLeftPoint?.lon,
            distance: 0,
            treatment: null,
            priceFrom: 0,
            priceTo: 0,
            opinion: 0.0,
            localizationLat: latitude,
            localizationLon: longitude,
          }),
          headers: {
            "Content-Type": "application/json",
          },
        });
        let json = [];

        if (response.ok) {
          json = await response.json();
          setSalons(json);
        } else {
          alert("Nie znaleziono rekordow  w bazie danych dla " + category);
        }
        setSalons(json);
        console.log("Kategory i bbox");
      }
    }
    load();
  }, [category, bbox]);

  useEffect(() => {
    async function load() {
      console.log(bbox);
      if (category !== null && bbox === null && salons.length === 0) {
        const response = await fetch(`${bsUrl}${category}`, {
          method: "POST",
          credentials: "include",
          body: JSON.stringify({
            serviceName: category,
            LatBtm: bounds._ne.lat,
            LonBtm: bounds._sw.lng,
            LatTop: bounds._sw.lat,
            LonTop: bounds._ne.lng,
            distance: 0,
            treatment: null,
            priceFrom: 0,
            priceTo: 0,
            opinion: 0.0,
            localizationLat: latitude,
            localizationLon: longitude,
          }),
          headers: {
            "Content-Type": "application/json",
          },
        });
        let json = [];

        if (response.ok) {
          json = await response.json();
          setSalons(json);
        } else {
          alert("Nie znaleziono rekordow  w bazie danych dla " + category);
        }

        console.log(json);
        console.log("Salons i category i bounds");
      }
    }
    load();
  }, [salons]);

  useEffect(() => {
    async function load() {
      if (category !== null && bbox !== null && salons.length === 0) {
        const response = await fetch(`${sUrl}/${category}`, {
          method: "POST",
          credentials: "include",
          body: JSON.stringify({
            serviceName: category,
            LatBtm: bbox.btmRightPoint?.lat,
            LonBtm: bbox.btmRightPoint?.lon,
            LatTop: bbox.topLeftPoint?.lat,
            LonTop: bbox.topLeftPoint?.lon,
            distance: 0,
            treatment: null,
            priceFrom: 0,
            priceTo: 0,
            opinion: 0.0,
            localizationLat: latitude,
            localizationLon: longitude,
          }),
          headers: {
            "Content-Type": "application/json",
          },
        });
        let json = [];

        if (response.ok) {
          json = await response.json();
          setSalons(json);
        } else {
          alert("Nie znaleziono rekordow  w bazie danych dla " + category);
        }

        console.log(salons.length);
        console.log("Salons i category i bbox");
      }
    }
    load();
  }, [salons]);

  useEffect(() => {
    function success(position) {
      setLatitude(position.coords.latitude);
      setLongitude(position.coords.longitude);
    }

    function errorF() {
      setError("Unable to retrieve your location");
    }

    navigator.geolocation.getCurrentPosition(success, errorF);
  }, []);

  return (
    <>
      <div className="App" style={{ height: "100vh" }}>
        <div className="MapContainer">
          <Menu setCategory={setCategory} />
          <Map
            mapStyle="https://api.tomtom.com/map/1/style/20.0.1-1/basic_main-lite.json?key=1ncwaIygtJ0KrjH5ssohlEKUGFf7G5Dv"
            mapProps={{ center: [100.0, 0], zoom: 10 }}
          >
            <Markers salons={salons} setSalons={setSalons} />

            <SearchAndZoom
              searchConfig={{ limit: 40 }}
              highlightSuggestions={true}
              emptyState={<div style={{ color: "red" }}>{"Nothing found"}</div>}
              position="top-right"
              renderMarker={true}
              onSearch={(item) => {
                setBbox(item.bbox);
              }}
              isAutoFocusActive={true}
            />

            <ZoomToLocalization
              latitude={latitude}
              longitude={longitude}
              setBounds={setBounds}
            />
          </Map>
        </div>
      </div>
    </>
  );
};

function ZoomToLocalization({ latitude, longitude, setBounds }) {
  const { map } = useMap();
  useEffect(() => {
    map.panTo([longitude, latitude]);
    setTimeout(() => {
      var bounds = map.getBounds();
      setBounds(bounds);
    }, 1000);
  }, [longitude, latitude]);
  return null;
}

export default ManageSalons;
