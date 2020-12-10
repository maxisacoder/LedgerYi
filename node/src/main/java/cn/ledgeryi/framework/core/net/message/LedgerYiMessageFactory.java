package cn.ledgeryi.framework.core.net.message;

import cn.ledgeryi.chainbase.core.message.MessageTypes;
import cn.ledgeryi.common.core.exception.P2pException;
import org.apache.commons.lang3.ArrayUtils;
import cn.ledgeryi.framework.common.overlay.message.MessageFactory;

/**
 * msg factory.
 */
public class LedgerYiMessageFactory extends MessageFactory {

  @Override
  public LedgerYiMessage create(byte[] data) throws Exception {
    try {
      byte type = data[0];
      byte[] rawData = ArrayUtils.subarray(data, 1, data.length);
      return create(type, rawData);
    } catch (final P2pException e) {
      throw e;
    } catch (final Exception e) {
      throw new P2pException(P2pException.TypeEnum.PARSE_MESSAGE_FAILED,
          "type=" + data[0] + ", len=" + data.length + ", error msg: " + e.getMessage());
    }
  }

  private LedgerYiMessage create(byte type, byte[] packed) throws Exception {
    MessageTypes receivedTypes = MessageTypes.fromByte(type);
    if (receivedTypes == null) {
      throw new P2pException(P2pException.TypeEnum.NO_SUCH_MESSAGE,
          "type=" + type + ", len=" + packed.length);
    }
    switch (receivedTypes) {
      case TX:
        return new TransactionMessage(packed);
      case BLOCK:
        return new BlockMessage(packed);
      case TXS:
        return new TransactionsMessage(packed);
      case BLOCKS:
        return new BlocksMessage(packed);
      case INVENTORY:
        return new InventoryMessage(packed);
      case FETCH_INV_DATA:
        return new FetchInvDataMessage(packed);
      case SYNC_BLOCK_CHAIN:
        return new SyncBlockChainMessage(packed);
      case BLOCK_CHAIN_INVENTORY:
        return new ChainInventoryMessage(packed);
      case ITEM_NOT_FOUND:
        return new ItemNotFound();
      case FETCH_BLOCK_HEADERS:
        return new FetchBlockHeadersMessage(packed);
      case TX_INVENTORY:
        return new TransactionInventoryMessage(packed);
      default:
        throw new P2pException(P2pException.TypeEnum.NO_SUCH_MESSAGE,
            receivedTypes.toString() + ", len=" + packed.length);
    }
  }
}
