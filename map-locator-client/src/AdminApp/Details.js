import { Modal, Text, TombacApp } from "tombac";
import { useState } from "react";
import EditBeautifingServiceType from "./EditBeautifingServiceType";
import { btUrl } from "/home/karpowic/Desktop/map-locator-all/map-locator-client/src/Constant.js";

export const Details = ({ bs }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [newBs, setNewBs] = useState(false);

  return (
    <>
      <p>
        name: {bs.name} <br></br>
        price: {bs.price}
        <button
          class="deleteButtonDetails"
          onClick={() => {
            async function load() {
              const response = await fetch(`${btUrl}${bs.id}`, {
                method: "DELETE",
                credentials: "include",
                headers: {
                  "Content-Type": "application/json",
                },
              });

              if (response.ok) {
              }
            }
            load();
          }}
        >
          <i class="fas fa-trash-alt trashDetails"></i>
        </button>
        <TombacApp theme={{ settings: { modalZIndex: 10 } }}>
          <button
            class="editButtonDetails"
            onClick={() => {
              setIsOpen(true);
              setNewBs(bs);
            }}
          >
            <i class="fas fa-edit editDetails"></i>
          </button>
          <Modal isOpen={isOpen} onRequestClose={() => setIsOpen(false)}>
            <Text padding={3}>
              <EditBeautifingServiceType bs={newBs} />
            </Text>
          </Modal>
        </TombacApp>
      </p>
    </>
  );
};

export default Details;
