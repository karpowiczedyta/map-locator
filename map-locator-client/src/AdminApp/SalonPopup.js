import Popup from "vector-maps/dist/features/Popup";
import Marker from "vector-maps/dist/features/Marker";
import { Modal, Text, TombacApp } from "tombac";
import { useEffect, useState } from "react";
import EditBeautifingService from "./EditBeautifingService";
import ShowDetails from "./ShowDetails";
import { btsUrl } from "/home/karpowic/Desktop/map-locator-all/map-locator-client/src/Constant.js";

export const SalonPopup = ({ salon, setSalons }) => {
  const [open, setOpen] = useState(false);
  const [bsId, setBsId] = useState(0);
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      {open && (
        <Popup
          offset={[0, 5]}
          tooltipPosition="bottom"
          tipSize={5}
          lng={salon.geometry.coordinates[0]}
          lat={salon.geometry.coordinates[1]}
          content={
            <>
              <div>
                <span class="salonType">{salon.properties.typeOfService}</span>
                <button
                  class="deleteButton"
                  onClick={() => {
                    async function load() {
                      const response = await fetch(
                        `${btsUrl}${salon.properties.id}`,
                        {
                          method: "DELETE",
                          credentials: "include",
                          headers: {
                            "Content-Type": "application/json",
                          },
                        }
                      );

                      if (response.ok) {
                        setSalons([]);
                      }
                    }
                    load();
                  }}
                >
                  <i class="fas fa-trash-alt trash"></i>
                </button>

                <TombacApp theme={{ settings: { modalZIndex: 10 } }}>
                  <button class="editButton" onClick={() => setIsOpen(true)}>
                    <i class="fas fa-edit edit"></i>
                  </button>
                  <Modal
                    isOpen={isOpen}
                    onRequestClose={() => setIsOpen(false)}
                  >
                    <Text padding={3}>
                      <EditBeautifingService
                        salonId={salon.properties.id}
                        setSalons={setSalons}
                      />
                    </Text>
                  </Modal>
                </TombacApp>

                <ShowDetails salon={salon} />
              </div>
              <h2>{salon.properties.nameOfSalon}</h2>
              <h3>
                <link
                  rel="stylesheet"
                  href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
                  integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
                  crossorigin="anonymous"
                />
                <i class="fas fa-star"></i> <i class="fas fa-star"></i>{" "}
                <i class="fas fa-star"></i>
                {"   " + salon.properties.opinion}
              </h3>
            </>
          }
          onPopupClose={() => {
            setOpen(false);
          }}
          onClickOutside={() => {
            setOpen(false);
          }}
          closeButton
        />
      )}

      <Marker
        key={salon.properties.id}
        lng={salon.geometry.coordinates[0]}
        lat={salon.geometry.coordinates[1]}
        onMouseLeave={(e) => console.log(salon)}
        onClick={(e) => {
          e.originalEvent.stopPropagation();
          setOpen(true);
          setBsId(salon.properties.id);
        }}
      />
    </>
  );
};

export default SalonPopup;
