package fr.utilix.eshop_api.entity.enumeration;

public enum OrderStatus {
    PENDING,          // Commande créée mais pas encore validée
    CONFIRMED,        // Paiement validé / commande confirmée
    PROCESSING,       // En préparation
    SHIPPED,          // Expédiée
    DELIVERED,        // Livrée au client
    CANCELLED,        // Annulée par le client ou le vendeur
    REFUNDED          // Remboursée
}
