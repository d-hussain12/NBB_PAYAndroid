

package io.token.tokenrequest;

import io.token.proto.common.token.TokenProtos;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_TokenRequest extends TokenRequest {

  private final TokenProtos.TokenRequestPayload tokenRequestPayload;

  private final TokenProtos.TokenRequestOptions tokenRequestOptions;

  AutoValue_TokenRequest(
      TokenProtos.TokenRequestPayload tokenRequestPayload,
      TokenProtos.TokenRequestOptions tokenRequestOptions) {
    if (tokenRequestPayload == null) {
      throw new NullPointerException("Null tokenRequestPayload");
    }
    this.tokenRequestPayload = tokenRequestPayload;
    if (tokenRequestOptions == null) {
      throw new NullPointerException("Null tokenRequestOptions");
    }
    this.tokenRequestOptions = tokenRequestOptions;
  }

  @Override
  public TokenProtos.TokenRequestPayload getTokenRequestPayload() {
    return tokenRequestPayload;
  }

  @Override
  public TokenProtos.TokenRequestOptions getTokenRequestOptions() {
    return tokenRequestOptions;
  }

  @Override
  public String toString() {
    return "TokenRequest{"
         + "tokenRequestPayload=" + tokenRequestPayload + ", "
         + "tokenRequestOptions=" + tokenRequestOptions
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TokenRequest) {
      TokenRequest that = (TokenRequest) o;
      return (this.tokenRequestPayload.equals(that.getTokenRequestPayload()))
           && (this.tokenRequestOptions.equals(that.getTokenRequestOptions()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= tokenRequestPayload.hashCode();
    h$ *= 1000003;
    h$ ^= tokenRequestOptions.hashCode();
    return h$;
  }

}
