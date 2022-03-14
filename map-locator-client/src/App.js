import "./App.css";
import Map from "vector-maps/dist/Map";
import SearchAndZoom from "vector-maps/dist/features/search/SearchAndZoom";
import React, { useEffect, useState } from "react";
import Menu from "./Menu.js";
import Markers from "./Markers.js";
import { useMap } from "vector-maps/dist/MapContext";
import { loadData } from "./Api";
import { LegolandApp, RequireAuth } from "legoland-sdk/dist/experimental";
import { TombacApp } from "tombac";
import { url, sUrl, bsUrl } from "./Constant";

function App() {
  const [bbox, setBbox] = useState(null);
  const [category, setCategory] = useState(null);
  const [salons, setSalons] = useState([]);
  const [open, setOpen] = useState([]);
  const [latitude, setLatitude] = useState(0);
  const [longitude, setLongitude] = useState(0);
  const [error, setError] = useState("");
  const [bounds, setBounds] = useState([]);
  const [distance, setDistance] = useState(0);
  const [treatment, setTreatment] = useState(null);
  const [priceFrom, setPriceFrom] = useState(0);
  const [priceTo, setPriceTo] = useState(0);
  const [opinion, setOpinion] = useState(0.0);
  const [changedOpinion, setChangedOpinion] = useState(0);
  const [optionToSearch, setOptionToSearch] = useState(0);

  useEffect(() => {
    console.log("=======================");
    console.log("category: " + category);
    console.log("distance: " + distance);
    console.log("optionToSearch: " + optionToSearch);
    console.log("treatment: " + treatment);
    console.log("priceFrom: " + priceFrom);
    console.log("priceTo: " + priceTo);
    console.log("setOpinion: " + opinion);
    console.log("=======================");
  }, [
    distance,
    opinion,
    optionToSearch,
    priceFrom,
    priceTo,
    opinion,
    changedOpinion,
    category,
  ]);

  useEffect(() => {
    async function load() {
      if (category !== null && bbox === null && optionToSearch !== 0) {
        const response = await fetch(`${bsUrl}${category}`, {
          method: "POST",
          credentials: "include",
          body: JSON.stringify({
            serviceName: category,
            LatBtm: bounds._ne.lat,
            LonBtm: bounds._sw.lng,
            LatTop: bounds._sw.lat,
            LonTop: bounds._ne.lng,
            distance: distance,
            treatment: treatment,
            priceFrom: priceFrom,
            priceTo: priceTo,
            opinion: opinion,
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
          console.log(json);
          setSalons(json);
        } else {
          alert("Nie znaleziono rekordow  w bazie danych dla:  " + category);
        }
        setSalons(json);
      }
    }
    load();
    console.log("wysylam wartosc z szukaj search != 0");
    setOptionToSearch(0);
  }, [category, optionToSearch]);

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
            distance: distance,
            treatment: treatment,
            priceFrom: priceFrom,
            priceTo: priceTo,
            opinion: opinion,
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
          console.log(json);
          setSalons(json);
        } else {
          alert("Nie znaleziono rekordow  w bazie danych dla 1 " + category);
        }
        setSalons(json);
        console.log("opinion,category");
      }
    }
    load();
    console.log("url: " + sUrl + category);
    setOptionToSearch(0);
  }, [category, changedOpinion]);

  useEffect(() => {
    if (category !== null && bbox !== null) {
      async function load() {
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
            treatment: treatment,
            priceFrom: priceFrom,
            priceTo: priceTo,
            opinion: opinion,
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
          console.log(json);
        } else {
          alert("Nie znaleziono rekordow  w bazie danych dla 1 " + category);
        }
        setSalons(json);
        console.log("opinion,bbox");
      }
      load();
    }
  }, [category, bbox, changedOpinion]);

  useEffect(() => {
    async function load() {
      console.log(bbox);
      if (category !== null && bbox !== null && optionToSearch !== 0) {
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
            treatment: treatment,
            priceFrom: priceFrom,
            priceTo: priceTo,
            opinion: opinion,
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
          console.log(json);
        } else {
          alert("Nie znaleziono rekordow  w bazie danych dla 1 " + category);
        }
        setSalons(json);
      }
    }
    load();
    setOptionToSearch(0);
  }, [category, bbox, optionToSearch]);

  useEffect(() => {
    function success(position) {
      setLatitude(position.coords.latitude);
      setLongitude(position.coords.longitude);
      console.log("long: " + position.coords.latitude);
      console.log("lat: " + position.coords.longitude);
    }

    console.log("localization");

    function errorF() {
      setError("Unable to retrieve your location");
    }

    navigator.geolocation.getCurrentPosition(success, errorF);
  }, []);

  return (
    <div className="App" style={{ height: "100vh" }}>
      <div className="MapContainer">
        <Menu
          setCategory={setCategory}
          setDistance={setDistance}
          setOptionToSearch={setOptionToSearch}
          setTreatment={setTreatment}
          setPriceFrom={setPriceFrom}
          setPriceTo={setPriceTo}
          setOpinion={setOpinion}
        />

        <Map
          mapStyle="https://api.tomtom.com/map/1/style/20.0.1-1/basic_main-lite.json?key=1ncwaIygtJ0KrjH5ssohlEKUGFf7G5Dv"
          mapProps={{ center: [100.0, 0], zoom: 10 }}
        >
          <Markers
            salons={salons}
            opinion={opinion}
            setOpinion={setOpinion}
            setChangedOpinion={setChangedOpinion}
          />

          <SearchAndZoom
            searchConfig={{ limit: 40 }}
            highlightSuggestions={true}
            emptyState={<div style={{ color: "red" }}>{"Nothing found"}</div>}
            position="top-right"
            renderMarker={true}
            onSearch={(item) => {
              setBbox(item.bbox);
              console.log(item.bbox);
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
  );
}

function ZoomToLocalization({ latitude, longitude, setBounds }) {
  const { map } = useMap();
  useEffect(() => {
    map.panTo([longitude, latitude]);
    console.log("long: " + longitude);
    console.log("lat: " + latitude);
    setTimeout(() => {
      var bounds = map.getBounds();
      setBounds(bounds);
    }, 1000);
  }, [longitude, latitude]);
  return null;
}

export default App;
